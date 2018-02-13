package com.github.grishberg.barcodescanner.di;

import com.github.grishberg.barcodescanner.common.Logger;
import com.github.grishberg.barcodescanner.form.CellRelationDao;
import com.github.grishberg.barcodescanner.form.CellRelationRepository;
import com.github.grishberg.barcodescanner.form.CellRelationRepositoryImpl;
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
    CellRelationRepository provideCellRelationRepository(Logger logger,
                                                         CellRelationDao cellRelationDao) {
        return new CellRelationRepositoryImpl(logger, cellRelationDao);
    }

    @Singleton
    @Provides
    public FormBuilderService provideFormBuilderService(Logger logger,
                                                        SheetsService sheetsService,
                                                        CellRelationRepository relationRepository,
                                                        LastDocumentProvider lastDocumentProvider) {
        return new FormBuilderService(logger, sheetsService, relationRepository, lastDocumentProvider);
    }
}
