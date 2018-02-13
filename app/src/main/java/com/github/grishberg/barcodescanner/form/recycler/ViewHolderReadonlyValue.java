package com.github.grishberg.barcodescanner.form.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.grishberg.barcodescanner.R;
import com.github.grishberg.barcodescanner.form.CellRelation;

/**
 * Created by grishberg on 10.02.18.
 */

public class ViewHolderReadonlyValue extends FormResultViewHolder {
    private final TextView valueTextView;

    private ViewHolderReadonlyValue(View itemView) {
        super(itemView);
        valueTextView = itemView.findViewById(R.id.form_result_item_readonly_value);
    }

    @Override
    void bind(CellRelation cellRelation) {
        setLabelText(cellRelation.getLabel());
        valueTextView.setText(cellRelation.getValue());
    }

    static FormResultViewHolder getInstance(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_relation, parent, false);
        return new ViewHolderReadonlyValue(v);
    }
}
