package com.github.grishberg.barcodescanner.form;

/**
 * Created by grishberg on 05.02.18.
 */

public class CellRelation {
    private final FormCellType cellType;
    private final boolean readOnly;
    private final int x;
    private String value;

    public CellRelation(CellRelation src) {
        this.cellType = src.cellType;
        this.readOnly = src.readOnly;
        this.x = src.x;
    }

    public CellRelation(FormCellType cellType, boolean readOnly, int x) {
        this.cellType = cellType;
        this.readOnly = readOnly;
        this.x = x;
    }

    public FormCellType getCellType() {
        return cellType;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public int getX() {
        return x;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CellRelation{" +
                "cellType=" + cellType +
                ", readOnly=" + readOnly +
                ", x=" + x +
                ", value='" + value + '\'' +
                '}';
    }
}
