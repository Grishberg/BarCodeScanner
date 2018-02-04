package com.github.grishberg.barcodescanner.di;

import com.github.grishberg.barcodescanner.common.Logger;
import com.github.grishberg.barcodescanner.main.MainController;
import com.github.grishberg.barcodescanner.main.MainControllerImpl;
import com.github.grishberg.barcodescanner.main.MainScreenService;
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
    MainScreenService provideMainScreenService(Logger logger, SheetsService sheetsService) {
        return new MainScreenService(logger, sheetsService);
    }

    @Provides
    MainController provideMainController(Logger logger, MainScreenService mainScreenService) {
        return new MainControllerImpl(mainScreenService, logger);
    }
}
