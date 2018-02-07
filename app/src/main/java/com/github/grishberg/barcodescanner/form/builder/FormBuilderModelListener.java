package com.github.grishberg.barcodescanner.form.builder;

import com.github.grishberg.barcodescanner.form.CellRelation;

import java.util.List;

/**
 * Created by grishberg on 07.02.18.
 */

public interface FormBuilderModelListener {
    void onRelationsReceived(List<CellRelation> relations);

    void onShowAddRelationDialog();
}
