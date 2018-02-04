package com.github.grishberg.barcodescanner.barcode;

import com.github.grishberg.barcodescanner.common.AbsServiceObservable;
import com.github.grishberg.barcodescanner.common.Logger;
import com.github.grishberg.barcodescanner.main.MainScreenService;

import javax.inject.Inject;

/**
 * Created by grishberg on 04.02.18.
 */

public class ScanService extends AbsServiceObservable<ScannerServiceListener> {
    private static final String TAG = ScanService.class.getSimpleName();
    private final Logger logger;
    private final MainScreenService mainScreenService;

    @Inject
    public ScanService(Logger logger, MainScreenService mainScreenService) {
        this.logger = logger;
        this.mainScreenService = mainScreenService;
    }

    public void onBarCodeFound(String code) {
        logger.d(TAG, "onBarCodeFound code:" + code);
        notifyShowMainScreen();
        mainScreenService.processBarCode(code);
    }

    private void notifyShowMainScreen() {
        for (ScannerServiceListener listener : listeners) {
            listener.onShowMainScreen();
        }
    }
}
