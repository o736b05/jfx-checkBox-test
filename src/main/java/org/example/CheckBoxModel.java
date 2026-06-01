package org.example;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CheckBoxModel {
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private boolean previousState = false;
    private boolean confirmedState = false;

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void confirmChange() {
        confirmedState = selected.get();
        previousState = selected.get();
    }

    public void revertChange() {
        selected.set(previousState);
    }
}
