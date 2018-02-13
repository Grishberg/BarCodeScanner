package com.github.grishberg.barcodescanner.form.dialog;

import com.github.grishberg.barcodescanner.form.builder.FormBuilderService;

/**
 * Created by grishberg on 06.02.18.
 */

public class AddRelationControllerImpl implements AddRelationController {
    private final FormBuilderService service;
    private AddRelationDialogView view;

    public AddRelationControllerImpl(FormBuilderService service) {
        this.service = service;
    }

    @Override
    public void setView(AddRelationDialogView addRelationDialogView) {
        view = addRelationDialogView;
    }

    @Override
    public void onPositiveClicked(String label, int cellColumnIndex, boolean checked, int typeIndex) {
        service.addRelation(label, cellColumnIndex, checked, typeIndex);
    }

    @Override
    public void onNegativeClicked() {
        view.dismissDialog();
    }
}
