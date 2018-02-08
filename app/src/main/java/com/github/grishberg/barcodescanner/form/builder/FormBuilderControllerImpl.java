package com.github.grishberg.barcodescanner.form.builder;

/**
 * Created by grishberg on 07.02.18.
 */

public class FormBuilderControllerImpl implements FormBuilderController {
    private FormBuilderFragmentView view;
    private final FormBuilderService service;

    public FormBuilderControllerImpl(FormBuilderFragmentView formBuilderFragmentView, FormBuilderService service) {
        this.service = service;
        this.view = formBuilderFragmentView;
    }

    @Override
    public void onFormCreated() {
        service.requestRelations();
    }

    @Override
    public void onAddRelationButtonClicked() {
        view.onShowAddRelationDialog();
    }
}
