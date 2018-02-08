package com.github.grishberg.barcodescanner.main;

import com.github.grishberg.barcodescanner.common.Logger;
import com.github.grishberg.barcodescanner.common.ValueObserver;
import com.github.grishberg.barcodescanner.form.CellRelation;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by grishberg on 04.02.18.
 */

public class MainControllerImpl implements MainController {
    private static final String TAG = MainControllerImpl.class.getSimpleName();
    private final MainScreenService service;
    private final Logger logger;
    private final MainScreenView view;

    public MainControllerImpl(MainScreenView view, MainScreenService service, Logger logger) {
        this.view = view;
        this.service = service;
        this.logger = logger;
    }

    @Override
    public void onOpenFileButtonClicked() {
        logger.d(TAG, "onOpenFileButtonClicked");
        service.showOpenFileDialog();
    }

    @Override
    public void openDocument(String path) {
        service.openExcelDocument(path);
    }

    @Override
    public void onCreated() {
        service.init();
    }
}
