package exercise.models;

import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SudokuField other = (SudokuField) obj;
        return value == other.value;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(value)
            .toHashCode();
    }
}
