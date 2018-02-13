package com.github.grishberg.barcodescanner.di;

import com.github.grishberg.barcodescanner.barcode.BarCodeController;
import com.github.grishberg.barcodescanner.common.Logger;
import com.github.grishberg.barcodescanner.form.builder.FormBuilderController;
import com.github.grishberg.barcodescanner.form.builder.FormBuilderService;
import com.github.grishberg.barcodescanner.form.dialog.AddRelationController;
import com.github.grishberg.barcodescanner.main.MainController;
import com.github.grishberg.barcodescanner.main.MainScreenService;
import com.github.grishberg.barcodescanner.sheets.SheetsService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by grishberg on 04.02.18.
 */
@Singleton
@Component(modules = {ApplicationModule.class, MainModule.class, BarCodeModule.class,
        RelationBuilderModule.class, FormModule.class, DbModule.class})
public interface AppComponent {
    BarCodeController provideBarCodeController();

    AddRelationController provideAddRelationController();

    FormBuilderService provideFormBuilderService();

    Logger provideLogger();

    MainScreenService provideMainScreenService();

    SheetsService provideSheetsService();
}
