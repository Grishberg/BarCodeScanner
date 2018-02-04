package com.github.grishberg.barcodescanner.common;

/**
 * Created by grishberg on 04.02.18.
 */

public interface ServiceListenerRegistrator<T> {
    void registerListener(T listener);

    void unregisterListener(T listener);
}
