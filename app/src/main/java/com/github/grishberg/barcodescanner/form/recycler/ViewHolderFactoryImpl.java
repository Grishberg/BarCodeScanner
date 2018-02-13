package com.github.grishberg.barcodescanner.form.recycler;

import android.view.ViewGroup;

import com.github.grishberg.barcodescanner.form.CellRelation;
import com.github.grishberg.barcodescanner.form.FormResultItemController;

/**
 * Created by grishberg on 10.02.18.
 */
public class ViewHolderFactoryImpl implements ViewHolderFactory {
    private static final int VIEW_TYPE_READONLY = 1;
    private static final int VIEW_TYPE_STRING_CELL = 2;
    private static final int VIEW_TYPE_NUMBER_CELL = 3;
    private final FormResultItemController controller;

    public ViewHolderFactoryImpl(FormResultItemController controller) {
        this.controller = controller;
    }

    @Override
    public int getViewType(CellRelation itemByPosition) {
        return 0;
    }

    @Override
    public FormResultViewHolder provideViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_STRING_CELL:
                return ViewHolderStringValue.getInstance(parent, controller);
            case VIEW_TYPE_NUMBER_CELL:
                return ViewHolderNumericValue.getInstance(parent, controller);
        }
        return ViewHolderReadonlyValue.getInstance(parent);
    }
}
