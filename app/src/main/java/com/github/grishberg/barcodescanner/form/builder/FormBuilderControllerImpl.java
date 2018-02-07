package com.github.grishberg.barcodescanner.form.builder;

import javax.inject.Inject;

/**
 * Created by grishberg on 07.02.18.
 */

public class FormBuilderControllerImpl implements FormBuilderController {
    private final FormBuilderService service;

    @Inject
    public FormBuilderControllerImpl(FormBuilderService service) {
        this.service = service;
    }

    @Override
    public void onFormCreated() {
        service.requestRelations();
    }

    @Override
    public void onAddRelationButtonClicked() {
        service.onAddRelationButtonClicked();
    }

    @Override
    public void registerFormBuilderListener(FormBuilderModelListener listener) {
        service.setListener(listener);
    }

    @Override
    public void unregisterFormBuilderListener(FormBuilderModelListener listener) {
        service.setListener(null);
    }
}
