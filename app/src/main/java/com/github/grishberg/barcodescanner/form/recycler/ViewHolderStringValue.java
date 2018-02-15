package com.github.grishberg.barcodescanner.form.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.github.grishberg.barcodescanner.R;
import com.github.grishberg.barcodescanner.form.CellRelation;
import com.github.grishberg.barcodescanner.form.FormResultItemController;

/**
 * Created by grishberg on 10.02.18.
 */

public class ViewHolderStringValue extends FormResultViewHolder {
    private final EditText valueEditText;
    private FormResultItemController controller;

    private ViewHolderStringValue(View itemView, FormResultItemController controller) {
        super(itemView);
        valueEditText = itemView.findViewById(R.id.form_cell_edit_text);
        this.controller = controller;
    }

    static FormResultViewHolder getInstance(ViewGroup parent, FormResultItemController controller) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_form_result_string, parent, false);
        return new ViewHolderStringValue(v, controller);
    }

    @Override
    void bind(CellRelation cellRelation) {
        setLabelText(cellRelation.getLabel());
        valueEditText.setText(cellRelation.getValue());
    }
}
