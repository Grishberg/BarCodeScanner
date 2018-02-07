package com.github.grishberg.barcodescanner.sheets;

import android.support.annotation.Nullable;

/**
 * Provides last opened document.
 */
public interface LastDocumentProvider {
    void updateLastDocument(String path);

    @Nullable
    String getLastDocument();
}
