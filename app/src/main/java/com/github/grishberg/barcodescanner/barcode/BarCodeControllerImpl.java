package com.github.grishberg.barcodescanner.barcode;

import com.github.grishberg.barcodescanner.common.Logger;

import javax.inject.Inject;

/**
 * Created by grishberg on 04.02.18.
 */
public class BarCodeControllerImpl implements BarCodeController {
    private static final String TAG = BarCodeControllerImpl.class.getSimpleName();
    private BarCodeScannerView view;
    private final Logger logger;
    private final ScanService scanService;

    public BarCodeControllerImpl(BarCodeScannerView view, Logger logger, ScanService scanService) {
        this.view = view;
        this.logger = logger;
        this.scanService = scanService;
    }

    @Override
    public void onBarCodeFound(String code) {
        scanService.onBarCodeFound(code);
    }

    @Override
    public void registerListener(ScannerServiceListener listener) {
        scanService.registerListener(listener);
    }

    @Override
    public void unregisterListener(ScannerServiceListener listener) {
        scanService.unregisterListener(listener);
    }

    @Override
    public void onUserVisibleHintChanged(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            view.startScanner();
        } else {
            view.stopScanner();
        }
    }
}
