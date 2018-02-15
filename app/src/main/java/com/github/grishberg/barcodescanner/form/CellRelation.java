package com.github.grishberg.barcodescanner.form;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by grishberg on 05.02.18.
 */
@Entity(indexes = {
        @Index(value = "docPath ASC", unique = false)
})
public class CellRelation {
    public static final int CELL_TYPE_STRING = 0;
    public static final int CELL_TYPE_NUMBER = 1;
    public static final int CELL_TYPE_SEARCH = 2;

    @Id
    private Long id;

    private String docPath;
    private int cellType;

    private boolean readOnly;

    private int x;

    private String value;

    private String label;

    public CellRelation(CellRelation src) {
        this.docPath = src.docPath;
        this.cellType = src.cellType;
        this.readOnly = src.readOnly;
        this.x = src.x;
        this.label = src.label;
    }

    public CellRelation(String label, int cellType, boolean readOnly, int x) {
        this.label = label;
        this.cellType = cellType;
        this.readOnly = readOnly;
        this.x = x;
    }

    @Generated(hash = 769196090)
    public CellRelation(Long id, String docPath, int cellType, boolean readOnly, int x,
                        String value, String label) {
        this.id = id;
        this.docPath = docPath;
        this.cellType = cellType;
        this.readOnly = readOnly;
        this.x = x;
        this.value = value;
        this.label = label;
    }

    @Generated(hash = 910735975)
    public CellRelation() {
    }

    public int getCellType() {
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

    public String getLabel() {
        return label;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocPath() {
        return this.docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public void setCellType(int cellType) {
        this.cellType = cellType;
    }

    public boolean getReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isSearch() {
        return cellType == CELL_TYPE_SEARCH;
    }
}
