package com.github.grishberg.barcodescanner.di;

import com.github.grishberg.barcodescanner.barcode.BarCodeController;
import com.github.grishberg.barcodescanner.main.MainController;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by grishberg on 04.02.18.
 */
@Singleton
@Component(modules = {ApplicationModule.class, MainModule.class, BarCodeModule.class})
public interface AppComponent {
    BarCodeController provideBarCodeController();

    MainController provideMainController();
}
