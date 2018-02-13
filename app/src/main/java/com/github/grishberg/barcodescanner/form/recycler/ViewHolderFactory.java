package com.github.grishberg.barcodescanner.form.recycler;

import android.view.ViewGroup;

import com.github.grishberg.barcodescanner.form.CellRelation;

/**
 * Created by grishberg on 10.02.18.
 */

public interface ViewHolderFactory {
    int getViewType(CellRelation itemByPosition);

    FormResultViewHolder provideViewHolder(ViewGroup parent, int viewType);
}
