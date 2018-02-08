package com.github.grishberg.barcodescanner.form.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.grishberg.barcodescanner.R;
import com.github.grishberg.barcodescanner.di.DiManager;
import com.github.grishberg.barcodescanner.form.builder.FormBuilderService;

/**
 * Created by grishberg on 05.02.18.
 */

public class AddRelationDialogView extends DialogFragment {
    private FormBuilderService formBuilderService;
    private AddRelationController controller;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        controller = DiManager.getAppComponent().provideAddRelationController();
        controller.setView(this);
        formBuilderService = DiManager.getAppComponent().provideFormBuilderService();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.cell_type_array, android.R.layout.simple_spinner_item);

        View rootView = inflater.inflate(R.layout.dialog_add_relation, null);
        final EditText addressEditText = rootView.findViewById(R.id.add_relation_cell_address);
        final CheckBox readOnly = rootView.findViewById(R.id.cell_readonly_checkbox);
        final Spinner typeSpinner = rootView.findViewById(R.id.cell_type_spinner);
        typeSpinner.setAdapter(adapter);

        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton(R.string.add_relation_button_text,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                int columnIndex = Integer.valueOf(addressEditText.getText()
                                        .toString());
                                controller.onPositiveClicked(columnIndex,
                                        readOnly.isChecked(),
                                        typeSpinner.getSelectedItemPosition());
                            }
                        })
                .setNegativeButton(R.string.add_relation_negative_button_text,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                controller.onNegativeClicked();
                            }
                        });
        return builder.create();
    }

    public void dismissDialog() {
        getDialog().cancel();
    }
}