package com.github.grishberg.barcodescanner.di;

import com.github.grishberg.barcodescanner.common.Logger;
import com.github.grishberg.barcodescanner.form.CellRelationRepository;
import com.github.grishberg.barcodescanner.form.CellRelationRepositoryImpl;
import com.github.grishberg.barcodescanner.form.builder.FormBuilderController;
import com.github.grishberg.barcodescanner.form.builder.FormBuilderControllerImpl;
import com.github.grishberg.barcodescanner.form.builder.FormBuilderService;
import com.github.grishberg.barcodescanner.sheets.LastDocumentProvider;
import com.github.grishberg.barcodescanner.sheets.SheetsService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by grishberg on 04.02.18.
 */
@Module
public class FormModule {
    @Singleton
    @Provides
    CellRelationRepository provideCellRelationRepository(Logger logger) {
        return new CellRelationRepositoryImpl(logger);
    }

    @Singleton
    @Provides
    public FormBuilderService provideFormBuilderService(Logger logger,
                                                        SheetsService sheetsService,
                                                        CellRelationRepository relationRepository,
                                                        LastDocumentProvider lastDocumentProvider) {
        return new FormBuilderService(logger, sheetsService, relationRepository, lastDocumentProvider);
    }

    @Provides
    public FormBuilderController provideFormBuilderController(FormBuilderService service) {
        return new FormBuilderControllerImpl(service);
    }
}
