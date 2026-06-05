package org.example.controls;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBox;
import org.example.models.CheckBoxModel;
import reactor.core.scheduler.Schedulers;
import reactor.core.Disposables;

public class MyCheckBox extends CheckBox {
    private final CheckBoxModel model;
    private final String checkBoxName;
    private final SimpleBooleanProperty processing = new SimpleBooleanProperty(false);
    private reactor.core.Disposable.Swap currentDisposable = Disposables.swap();

    public MyCheckBox(String text) {
        super(text);
        checkBoxName = text;
        this.model = new CheckBoxModel();
        this.disableProperty().bind(processing);
        this.selectedProperty().bindBidirectional(model.selectedProperty());
        setupAsyncHandler();
    }

    private void setupAsyncHandler() {
        selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (processing.get()) return;
            processing.set(true);
            // сервис и подписка
            currentDisposable.update(model.processClick(newVal)
                    .subscribeOn(Schedulers.boundedElastic())
                    .publishOn(Schedulers.fromExecutor(Platform::runLater))
                    .subscribe(
                            (result) -> {
                                // Успех
                                System.out.println(checkBoxName + ", Операция выполнена");
                                model.confirmChange();
                                processing.set(false);
                            },
                            (error) -> {
                                // Ошибка
                                System.out.println(checkBoxName + ", Ошибка: " + error.getMessage());
                                model.revertChange();
                                setSelected(oldVal);
                                processing.set(false);
                            }
                    ));
        });
    }
}
