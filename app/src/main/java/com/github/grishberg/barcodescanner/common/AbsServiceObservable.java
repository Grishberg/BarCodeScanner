package com.github.grishberg.barcodescanner.common;

import java.util.ArrayList;

/**
 * Created by grishberg on 04.02.18.
 */
public abstract class AbsServiceObservable<T> implements ServiceListenerRegistrator<T> {
    protected final ArrayList<T> listeners = new ArrayList<>();

    @Override
    public void registerListener(T listener) {
        listeners.add(listener);
    }

    @Override
    public void unregisterListener(T listener) {
        listeners.remove(listener);
    }
}
