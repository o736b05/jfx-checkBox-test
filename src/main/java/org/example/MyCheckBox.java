package org.example;
import javafx.scene.control.CheckBox;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class MyCheckBox extends CheckBox {
    private final CheckBoxModel model;
    private final String checkBoxName;
    private volatile boolean processing = false;
    private reactor.core.Disposable currentDisposable;

    public MyCheckBox(String text) {
        super(text);
        checkBoxName = text;
        this.model = new CheckBoxModel();
        // Связываем UI состояние с моделью
        this.selectedProperty().bindBidirectional(model.selectedProperty());
        setupAsyncHandler();
    }

    private void setupAsyncHandler() {
        // Используем слушатель изменений свойства selected
        selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (processing)
                return;
            // Отменяем предыдущую подписку, если она активна
            if (currentDisposable != null && !currentDisposable.isDisposed()) {
                currentDisposable.dispose();
            }
            processing = true;
            this.setDisable(true);
            currentDisposable = Mono.fromCallable(() -> {
                        // Имитация долгой операции
                        Thread.sleep(3000);
                        Functions.MaybeGetException();
                        return newVal;
                    })
                    .subscribeOn(Schedulers.boundedElastic())
                    .subscribe(
                            result -> {
                                // SUCCEEDED
                                System.out.println(String.format(checkBoxName + ", операция выполнена"));
                                model.confirmChange();
                                finishProcessing();
                            },
                            error -> {
                                // FAILED
                                System.out.println(String.format(checkBoxName + ", " + error.getMessage() + ", операция вызвала ошибку"));
                                model.revertChange();
                                finishProcessing();
                            }
                    );
        });
    }

    private void finishProcessing() {
        javafx.application.Platform.runLater(() -> {
            setDisable(false);
            processing = false;
            if (currentDisposable != null) {
                currentDisposable = null;
            }
        });
    }
}
