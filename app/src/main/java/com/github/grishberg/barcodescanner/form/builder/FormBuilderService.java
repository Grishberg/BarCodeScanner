package com.github.grishberg.barcodescanner.form.builder;

import com.github.grishberg.barcodescanner.common.Logger;
import com.github.grishberg.barcodescanner.form.CellRelation;
import com.github.grishberg.barcodescanner.form.CellRelationRepository;
import com.github.grishberg.barcodescanner.form.FormCellType;
import com.github.grishberg.barcodescanner.sheets.LastDocumentProvider;
import com.github.grishberg.barcodescanner.sheets.SheetsService;

import java.util.List;

import javax.inject.Inject;

/**
 * Creates Cells representation form.
 */
public class FormBuilderService {
    private static final String TAG = FormBuilderService.class.getSimpleName();
    private final SheetsService sheetsService;
    private final Logger logger;
    private final CellRelationRepository relationRepository;
    private FormBuilderModelListener listener;
    private LastDocumentProvider lastDocumentProvider;

    @Inject
    public FormBuilderService(Logger logger,
                              SheetsService sheetsService,
                              CellRelationRepository relationRepository,
                              LastDocumentProvider lastDocumentProvider) {
        this.sheetsService = sheetsService;
        this.logger = logger;
        this.relationRepository = relationRepository;
        this.lastDocumentProvider = lastDocumentProvider;
    }

    public void requestRelations() {
        logger.d(TAG, "requestRelations");
        String lastDoc = lastDocumentProvider.getLastDocument();
        if (lastDoc == null) {
            //TODO: show empty message.
            logger.d(TAG, "requestRelations: lastDoc is empty");
            return;
        }
        relationRepository.findRepresentation(lastDoc, new CellRelationRepository.OnRepresentationLoadedListener() {
            @Override
            public void onRepresentationLoaded(List<CellRelation> representations) {
                logger.d(TAG, "requestRelations: onRepresentationLoaded");
                notifyShowRepresentations(representations);
            }

            @Override
            public void onRepresentationNotFound() {
                // nothing to do.
                logger.d(TAG, "requestRelations: onRepresentationNotFound");
            }
        });
    }

    private void notifyShowRepresentations(List<CellRelation> representations) {
        if (listener != null) {
            listener.onRelationsReceived(representations);
        }
    }

    public void registerFormBuilderServiceListener(FormBuilderModelListener listener) {
        this.listener = listener;
    }

    public void unregisterFormBuilderServiceListener(FormBuilderModelListener listener) {
        this.listener = null;
    }

    public void addRelation(String label, int cellColumnIndex, boolean readonly, int cellType) {
        CellRelation relation = new CellRelation(label, cellType, readonly, cellColumnIndex);
        relationRepository.addRelationForCurrentDoc(relation);
        requestRelations();
    }
}
