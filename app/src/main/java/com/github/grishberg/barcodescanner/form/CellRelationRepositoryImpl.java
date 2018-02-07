package com.github.grishberg.barcodescanner.form;

import com.github.grishberg.barcodescanner.common.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grishberg on 07.02.18.
 */

public class CellRelationRepositoryImpl implements CellRelationRepository {
    private static final String TAG = CellRelationRepositoryImpl.class.getSimpleName();
    private final Logger logger;
    private final ArrayList<Object> cellsRelations;

    public CellRelationRepositoryImpl(Logger logger) {
        this.logger = logger;
        cellsRelations = new ArrayList<>();
        cellsRelations.add(new CellRelation(FormCellType.STRING, true, 0));
        cellsRelations.add(new CellRelation(FormCellType.NUMBER, true, 4));
    }

    @Override
    public void findRepresentation(String filePath, OnRepresentationLoadedListener listener) {

    }

    @Override
    public void storeRepresentatin(String filePath, List<CellRelation> representations) {

    }
}
