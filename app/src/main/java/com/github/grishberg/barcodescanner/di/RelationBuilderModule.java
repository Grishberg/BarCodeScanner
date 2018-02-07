package com.github.grishberg.barcodescanner.di;

import com.github.grishberg.barcodescanner.form.dialog.AddRelationController;
import com.github.grishberg.barcodescanner.form.dialog.AddRelationControllerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by grishberg on 06.02.18.
 */
@Module
public class RelationBuilderModule {
    @Provides
    public AddRelationController provideAddRelationController() {
        return new AddRelationControllerImpl();
    }
}
