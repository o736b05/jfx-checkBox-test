package org.example.models;
import org.example.services.AsyncService;
import org.example.utils.Functions;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class CheckBoxModel implements AsyncService {
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private boolean previousState = false;

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void confirmChange() {
        previousState = selected.get();
    }

    public void revertChange() {
        selected.set(previousState);
    }

    @Override
    public Mono<Boolean> processClick(boolean newValue) {
        boolean currentValue = selected.get();
        return Mono.fromCallable(() -> {
            if (currentValue) {
                Thread.sleep(1500);
            } else {
                Thread.sleep(500);
            }
            Functions.MaybeGetException();
            return newValue;
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
