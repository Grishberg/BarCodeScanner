package com.github.grishberg.barcodescanner.common;

/**
 * Created by grishberg on 04.02.18.
 */

public interface ValueObserver<T> {
    void onValueChanged(T value);
}
