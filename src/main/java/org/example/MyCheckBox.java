package org.example;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBox;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.core.Disposables;

public class MyCheckBox extends CheckBox {
    private final CheckBoxModel model;
    private final String checkBoxName;
    private final SimpleBooleanProperty processing = new SimpleBooleanProperty(false);
    private Disposable.Swap currentDisposable;

    public MyCheckBox(String text) {
        super(text);
        checkBoxName = text;
        this.model = new CheckBoxModel();
        this.disableProperty().bind(processing);
        this.selectedProperty().bindBidirectional(model.selectedProperty());
        setupAsyncHandler();
    }

    private void setupAsyncHandler() {
        // Используем слушатель изменений свойства selected
        selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (processing.get())
                return;
            if (currentDisposable == null) {
                currentDisposable = Disposables.swap();
            }
            processing.set(!processing.get()); // инвертирование флага
            currentDisposable.update(Mono.fromCallable(() -> {
                // Имитация долгой операции
                Thread.sleep(1000);
                Functions.MaybeGetException();
                return newVal;
            }).subscribeOn(Schedulers.boundedElastic()).subscribe(
                    result -> { // SUCCEEDED
                        javafx.application.Platform.runLater(() -> {
                            System.out.println(String.format(checkBoxName + ", операция выполнена"));
                            model.confirmChange();
                            processing.set(!processing.get()); // инвертирование флага
                        });
                    },
                    error -> { // FAILED
                        javafx.application.Platform.runLater(() -> {
                            System.out.println(String.format(checkBoxName + ", " + error.getMessage() + ", операция вызвала ошибку"));
                            model.revertChange();
                            processing.set(!processing.get()); // инвертирование флага
                        });
                    }
            ));
        });
    }
}
