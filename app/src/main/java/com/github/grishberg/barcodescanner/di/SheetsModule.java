package com.github.grishberg.barcodescanner.di;

import android.content.Context;

import com.github.grishberg.barcodescanner.common.Logger;
import com.github.grishberg.barcodescanner.sheets.LastDocumentProvider;
import com.github.grishberg.barcodescanner.sheets.LastDocumentProviderImpl;
import com.github.grishberg.barcodescanner.sheets.SheetsService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by grishberg on 04.02.18.
 */
@Module
public class SheetsModule {
    @Singleton
    @Provides
    public LastDocumentProvider provideLastDocumentProvider(Context appContext) {
        return new LastDocumentProviderImpl(appContext);
    }

    @Singleton
    @Provides
    public SheetsService provideSheetsService(Logger logger, Context context) {
        return new SheetsService(logger, context);
    }
}
