package com.github.grishberg.barcodescanner.form;

import java.util.List;

/**
 * Store and load cell representation mapping.
 */
public interface CellRelationRepository {
    void findRepresentation(String filePath, OnRepresentationLoadedListener listener);

    void storeRepresentatin(String filePath, List<CellRelation> representations);

    interface OnRepresentationLoadedListener {
        void onRepresentationLoaded(List<CellRelation> representations);

        void onRepresentationNotFound();
    }
}
