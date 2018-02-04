package com.github.grishberg.barcodescanner.main;

import com.github.grishberg.barcodescanner.common.Logger;
import com.github.grishberg.barcodescanner.common.ValueObserver;

import javax.inject.Inject;

/**
 * Created by grishberg on 04.02.18.
 */

public class MainControllerImpl implements MainController {
    private static final String TAG = MainControllerImpl.class.getSimpleName();
    private final MainScreenService service;
    private final Logger logger;

    @Inject
    public MainControllerImpl(MainScreenService service, Logger logger) {
        this.service = service;
        this.logger = logger;
    }

    @Override
    public void registerListener(MainServiceStateChangeListener listener) {
        service.registerListener(listener);
    }

    @Override
    public void unregisterListener(MainServiceStateChangeListener listener) {
        service.unregisterListener(listener);
    }

    @Override
    public void onScannerButtonClicked() {
        logger.d(TAG, "onScannerButtonClicked");
        service.showBarCodeScanner();
    }

    @Override
    public void onOpenFileButtonClicked() {
        logger.d(TAG, "onOpenFileButtonClicked");
        service.showOpenFileDialog();
    }

    @Override
    public void registerBarcodeObserver(ValueObserver<String> observer) {
        service.registerBarcodeObserver(observer);
    }

    @Override
    public void unregisterBarcodeObserver(ValueObserver<String> observer) {
        service.unregisterBarcodeObserver(observer);
    }
}
