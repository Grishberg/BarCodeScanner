package com.github.grishberg.barcodescanner.form.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.grishberg.barcodescanner.R;
import com.github.grishberg.barcodescanner.form.CellRelation;

/**
 * Created by grishberg on 10.02.18.
 */

public abstract class FormResultViewHolder extends RecyclerView.ViewHolder {
    private final TextView labelTextView;

    public FormResultViewHolder(View itemView) {
        super(itemView);
        labelTextView = itemView.findViewById(R.id.cell_label);
    }

    abstract void bind(CellRelation cellRelation);

    protected void setLabelText(String text) {
        labelTextView.setText(text);
    }
}
