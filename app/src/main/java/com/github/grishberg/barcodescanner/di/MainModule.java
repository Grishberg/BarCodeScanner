package com.github.grishberg.barcodescanner.di;

import android.content.Context;

import com.github.grishberg.barcodescanner.common.Logger;
import com.github.grishberg.barcodescanner.form.CellRelationRepository;
import com.github.grishberg.barcodescanner.main.MainController;
import com.github.grishberg.barcodescanner.main.MainControllerImpl;
import com.github.grishberg.barcodescanner.main.MainScreenService;
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
public class MainModule {
    @Singleton
    @Provides
    LastDocumentProvider provideLastDocumentProvider(Context appContext) {
        return new LastDocumentProviderImpl(appContext);
    }

    @Singleton
    @Provides
    MainScreenService provideMainScreenService(Logger logger, SheetsService sheetsService,
                                               LastDocumentProvider lastDocumentProvider,
                                               CellRelationRepository cellRelationRepository) {
        return new MainScreenService(logger, sheetsService,
                lastDocumentProvider, cellRelationRepository);
    }
}
