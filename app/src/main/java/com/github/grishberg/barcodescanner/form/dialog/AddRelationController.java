package com.github.grishberg.barcodescanner.form.dialog;

/**
 * Created by grishberg on 05.02.18.
 */

public interface AddRelationController {
    void onPositiveClicked(String label, int cellColumnIndex, boolean checked, int selectedItemPosition);

    void onNegativeClicked();

    void setView(AddRelationDialogView addRelationDialogView);
}
