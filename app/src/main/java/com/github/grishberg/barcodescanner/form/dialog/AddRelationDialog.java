package com.github.grishberg.barcodescanner.form.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.github.grishberg.barcodescanner.R;
import com.github.grishberg.barcodescanner.di.DiManager;

/**
 * Created by grishberg on 05.02.18.
 */

public class AddRelationDialog extends DialogFragment {
    private AddRelationController controller;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        controller = DiManager.getAppComponent().provideAddRelationController();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_add_relation, null))
                // Add action buttons
                .setPositiveButton(R.string.add_relation_button_text,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // sign in the user ...
                            }
                        })
                .setNegativeButton(R.string.add_relation_negative_button_text,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AddRelationDialog.this.getDialog().cancel();
                            }
                        });
        return builder.create();
    }
}