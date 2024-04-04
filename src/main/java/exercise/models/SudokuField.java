package exercise.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class SudokuField {
    private int value;
    private List<PropertyChangeListener> listeners;

    public SudokuField(int value) {
        this.value = value;
        this.listeners = new ArrayList<>();
    }

    public SudokuField() {
        this.value = 0;
        this.listeners = new ArrayList<>();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int newVal) {
        int oldVal = this.value;
        this.value = newVal;
        firePropertyChange("value-changed", oldVal, newVal);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.listeners.add(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.listeners.remove(listener);
    }

    protected void firePropertyChange(String propertyName, Object oldVal, Object newVal) {
        for (PropertyChangeListener listener : listeners) {
            listener.propertyChange(new PropertyChangeEvent(this, propertyName, oldVal, newVal));
        }
    }
}
