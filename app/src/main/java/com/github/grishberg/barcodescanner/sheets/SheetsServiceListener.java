package com.github.grishberg.barcodescanner.sheets;

import com.github.grishberg.barcodescanner.form.CellRelation;

import java.util.List;

/**
 * Created by grishberg on 04.02.18.
 */

public interface SheetsServiceListener {
    /**
     * Events when found sheets cell by key.
     *
     * @param result
     */
    void onFoundSheetsCell(List<CellRelation> result);
}
