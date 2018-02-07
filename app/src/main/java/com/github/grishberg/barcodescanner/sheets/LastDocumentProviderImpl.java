package com.github.grishberg.barcodescanner.sheets;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.grishberg.barcodescanner.R;

import javax.inject.Inject;

/**
 * Created by grishberg on 06.02.18.
 */
public class LastDocumentProviderImpl implements LastDocumentProvider {
    private static final String LAST_DOCUMENT = "LAST_DOCUMENT";
    private final SharedPreferences sharedPreferences;

    @Inject
    public LastDocumentProviderImpl(Context appContext) {
        sharedPreferences = appContext.getSharedPreferences(
                appContext.getString(R.string.preference_last_doc_key), Context.MODE_PRIVATE);
    }

    @Override
    public void updateLastDocument(String path) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_DOCUMENT, path);
        editor.apply();
    }

    @Override
    public String getLastDocument() {
        return sharedPreferences.getString(LAST_DOCUMENT, null);
    }
}
