package com.github.grishberg.barcodescanner.di;

import com.github.grishberg.barcodescanner.form.builder.FormBuilderService;
import com.github.grishberg.barcodescanner.form.dialog.AddRelationController;
import com.github.grishberg.barcodescanner.form.dialog.AddRelationControllerImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by grishberg on 06.02.18.
 */
@Module
public class RelationBuilderModule {
    @Provides
    public AddRelationController provideAddRelationController(FormBuilderService service) {
        return new AddRelationControllerImpl(service);
    }
}
