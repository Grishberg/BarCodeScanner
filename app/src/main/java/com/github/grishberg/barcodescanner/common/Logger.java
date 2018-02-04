package com.github.grishberg.barcodescanner.common;

/**
 * Created by grishberg on 04.02.18.
 */
public interface Logger {
    void d(String tag, String message);

    void e(String tag, String message);

    void e(String tag, String message, Throwable t);
}

