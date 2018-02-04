package com.github.grishberg.barcodescanner.common;

import android.util.Log;

/**
 * Created by grishberg on 04.02.18.
 */
public class LogTagLogger implements Logger {
    @Override
    public void d(String tag, String message) {
        Log.d(tag, message);
    }

    @Override
    public void e(String tag, String message) {
        Log.e(tag, message);
    }

    @Override
    public void e(String tag, String message, Throwable t) {
        Log.e(tag, message, t);
    }
}
