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
    private String currentFilePath;
    private final ArrayList<CellRelation> currentRelation = new ArrayList<>();

    public CellRelationRepositoryImpl(Logger logger) {
        this.logger = logger;
        currentRelation.add(new CellRelation(FormCellType.STRING, true, 0));
        currentRelation.add(new CellRelation(FormCellType.NUMBER, true, 4));
    }

    @Override
    public void findRepresentation(String filePath, OnRepresentationLoadedListener listener) {
        if (listener != null) {
            listener.onRepresentationLoaded(currentRelation);
        }
    }

    @Override
    public void storeRelations(String filePath, List<CellRelation> relations) {
        currentRelation.clear();
        currentRelation.addAll(relations);
    }

    @Override
    public void setCurrentDocumentName(String filePath) {
        currentFilePath = filePath;
    }

    @Override
    public void addRelationForCurrentDoc(CellRelation relation) {
        currentRelation.add(relation);
    }
}
