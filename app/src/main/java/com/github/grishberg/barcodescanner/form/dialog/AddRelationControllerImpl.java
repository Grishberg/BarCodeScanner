package com.github.grishberg.barcodescanner.form.dialog;

import com.github.grishberg.barcodescanner.form.builder.FormBuilderService;

/**
 * Created by grishberg on 06.02.18.
 */

public class AddRelationControllerImpl implements AddRelationController {
    public static final int TYPE_SEARCH_POSITION = 2;
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
    public void onPositiveClicked(String label,
                                  int cellColumnIndex,
                                  boolean checked,
                                  int typeIndex) {
        service.addRelation(label, cellColumnIndex, checked, typeIndex);
    }

    @Override
    public void onNegativeClicked() {
        view.dismissDialog();
    }

    @Override
    public void onTypeChanged(int position) {
        if(position == TYPE_SEARCH_POSITION) {
            view.enableSearchMode();
        } else {
            view.enableViewDataMode();
        }
    }
}
