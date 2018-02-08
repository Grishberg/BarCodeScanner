package com.github.grishberg.barcodescanner.main;

import com.github.grishberg.barcodescanner.common.AbsServiceObservable;
import com.github.grishberg.barcodescanner.common.Logger;
import com.github.grishberg.barcodescanner.common.ObservableValue;
import com.github.grishberg.barcodescanner.common.ValueObserver;
import com.github.grishberg.barcodescanner.form.CellRelation;
import com.github.grishberg.barcodescanner.form.CellRelationRepository;
import com.github.grishberg.barcodescanner.form.FormCellType;
import com.github.grishberg.barcodescanner.sheets.LastDocumentProvider;
import com.github.grishberg.barcodescanner.sheets.SheetsColumnRequest;
import com.github.grishberg.barcodescanner.sheets.SheetsService;
import com.github.grishberg.barcodescanner.sheets.SheetsServiceListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by grishberg on 04.02.18.
 */
public class MainScreenService extends AbsServiceObservable<MainServiceStateChangeListener> implements SheetsServiceListener {
    private static final String TAG = MainScreenService.class.getSimpleName();
    private final Logger logger;
    private ObservableValue<String> barCode = new ObservableValue<>();
    private ObservableValue<List<CellRelation>> resultCells = new ObservableValue<>();
    private final SheetsService sheetsService;
    private final LastDocumentProvider lastDocumentProvider;
    private final CellRelationRepository cellRelationStorage;
    private List<CellRelation> cellsRelations;

    @Inject
    public MainScreenService(Logger logger,
                             SheetsService sheetsService,
                             LastDocumentProvider lastDocumentProvider,
                             CellRelationRepository cellRelationStorage) {
        this.logger = logger;
        this.sheetsService = sheetsService;
        this.lastDocumentProvider = lastDocumentProvider;
        this.cellRelationStorage = cellRelationStorage;
        sheetsService.registerListener(this);
    }

    public void init() {
        checkLastOpenedDoc();
    }

    private void checkLastOpenedDoc() {
        String lastDoc = lastDocumentProvider.getLastDocument();
        if (lastDoc == null) {
            showOpenFileDialog();
            return;
        }
        sheetsService.setDocFileName(lastDoc);
        checkForRelationsAndOpenNextScreen(lastDoc);
    }

    private void showAddRelationsScreen() {
        for (MainServiceStateChangeListener listener : listeners) {
            listener.onShowAddRelationsScreen();
        }
    }

    public void processBarCode(String code) {
        logger.d(TAG, "processBarCode: " + code);
        barCode.changeValue(code);
        sheetsService.findSheetsCell(new SheetsColumnRequest(code, cellsRelations));
        notifyShowResultScreen();
    }

    private void notifyShowResultScreen() {
        for (MainServiceStateChangeListener listener : listeners) {
            listener.onShowMainScreen();
        }
    }

    public void openExcelDocument(String path) {
        sheetsService.setDocFileName(path);
        lastDocumentProvider.updateLastDocument(path);

        checkForRelationsAndOpenNextScreen(path);
    }

    private void checkForRelationsAndOpenNextScreen(String path) {
        cellRelationStorage.findRepresentation(path, new CellRelationRepository.OnRepresentationLoadedListener() {
            @Override
            public void onRepresentationLoaded(List<CellRelation> relations) {
                cellsRelations = relations;
                showBarcodeScreen();
            }

            @Override
            public void onRepresentationNotFound() {
                showAddRelationsScreen();
            }
        });
    }

    private void showBarcodeScreen() {
        logger.d(TAG, "showBarcodeScreen");
        for (MainServiceStateChangeListener listener : listeners) {
            listener.onShowBarCodeScanner();
        }
    }

    public void showOpenFileDialog() {
        logger.d(TAG, "showOpenFileDialog");
        for (MainServiceStateChangeListener listener : listeners) {
            listener.onOpenFileChooser();
        }
    }

    @Override
    public void onFoundSheetsCell(List<CellRelation> result) {
        resultCells.changeValue(result);
    }

    public void registerBarcodeObserver(ValueObserver<String> observer) {
        barCode.registerObserver(observer);
    }

    public void unregisterBarcodeObserver(ValueObserver<String> observer) {
        barCode.unregisterObserver(observer);
    }

    public void registerResultCellsObserver(ValueObserver<List<CellRelation>> observer) {
        resultCells.registerObserver(observer);
    }

    public void unregisterResultCellsObserver(ValueObserver<List<CellRelation>> observer) {
        resultCells.unregisterObserver(observer);
    }
}
