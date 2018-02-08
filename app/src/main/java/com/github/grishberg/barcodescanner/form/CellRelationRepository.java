package com.github.grishberg.barcodescanner.form;

import java.util.List;

/**
 * Store and load cell representation mapping.
 */
public interface CellRelationRepository {
    void setCurrentDocumentName(String filePath);

    void addRelationForCurrentDoc(CellRelation relation);

    void findRepresentation(String filePath, OnRepresentationLoadedListener listener);

    void storeRelations(String filePath, List<CellRelation> representations);

    interface OnRepresentationLoadedListener {
        void onRepresentationLoaded(List<CellRelation> representations);

        void onRepresentationNotFound();
    }
}
