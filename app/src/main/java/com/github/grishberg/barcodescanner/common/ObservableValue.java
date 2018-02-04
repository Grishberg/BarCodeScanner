package com.github.grishberg.barcodescanner.common;

import java.util.ArrayList;

/**
 * Created by grishberg on 04.02.18.
 */

public class ObservableValue<T> {
    private T value;
    private ArrayList<ValueObserver<T>> observers = new ArrayList<>();

    public void changeValue(T newValue) {
        value = newValue;
        notifyValueChanged();
    }

    private void notifyValueChanged() {
        for (ValueObserver<T> observer : observers) {
            observer.onValueChanged(value);
        }
    }

    public void registerObserver(ValueObserver<T> observer) {
        observers.add(observer);
        if (value != null) {
            observer.onValueChanged(value);
        }
    }

    public void unregisterObserver(ValueObserver<T> observer) {
        observers.remove(observer);
    }
}
