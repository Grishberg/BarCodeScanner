package com.github.grishberg.barcodescanner.sheets;

import com.github.grishberg.barcodescanner.form.CellRelation;

import java.util.List;

/**
 * Request for searching sheets column.
 */
public class SheetsColumnRequest {
    private final String searchString;
    private final List<CellRelation> cellRepresentations;

    public SheetsColumnRequest(String searchString, List<CellRelation> cellRepresentations) {
        this.searchString = searchString;
        this.cellRepresentations = cellRepresentations;
    }

    public String getSearchString() {
        return searchString;
    }

    public List<CellRelation> getCellRepresentations() {
        return cellRepresentations;
    }
}
