package com.github.grishberg.barcodescanner.di;

import com.github.grishberg.barcodescanner.barcode.BarCodeController;
import com.github.grishberg.barcodescanner.barcode.BarCodeControllerImpl;
import com.github.grishberg.barcodescanner.barcode.ScanService;
import com.github.grishberg.barcodescanner.common.Logger;
import com.github.grishberg.barcodescanner.main.MainScreenService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by grishberg on 04.02.18.
 */
@Module
public class BarCodeModule {

    @Singleton
    @Provides
    ScanService provideScanService(Logger logger, MainScreenService mainScreenService) {
        return new ScanService(logger, mainScreenService);
    }

    @Provides
    public BarCodeController provideBarCodeController(Logger logger, ScanService scanService) {
        return new BarCodeControllerImpl(logger, scanService);
    }
}
