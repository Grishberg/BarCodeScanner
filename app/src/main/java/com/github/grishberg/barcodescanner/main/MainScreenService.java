package com.github.grishberg.barcodescanner.main;

import com.github.grishberg.barcodescanner.common.AbsServiceObservable;
import com.github.grishberg.barcodescanner.common.Logger;
import com.github.grishberg.barcodescanner.common.ObservableValue;
import com.github.grishberg.barcodescanner.common.ValueObserver;
import com.github.grishberg.barcodescanner.sheets.FoundCellResult;
import com.github.grishberg.barcodescanner.sheets.SheetsService;
import com.github.grishberg.barcodescanner.sheets.SheetsServiceListener;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by grishberg on 04.02.18.
 */
public class MainScreenService extends AbsServiceObservable<MainServiceStateChangeListener> implements SheetsServiceListener {
    private static final String TAG = MainScreenService.class.getSimpleName();
    private final Logger logger;
    private ObservableValue<String> barCode = new ObservableValue<>();
    private final SheetsService sheetsService;

    @Inject
    public MainScreenService(Logger logger, SheetsService sheetsService) {
        this.logger = logger;
        this.sheetsService = sheetsService;
        sheetsService.registerListener(this);
    }

    public void showBarCodeScanner() {
        for (MainServiceStateChangeListener listener : listeners) {
            listener.onShowBarCodeScanner();
        }
    }

    public void processBarCode(String code) {
        logger.d(TAG, "processBarCode: " + code);
        barCode.changeValue(code);
        sheetsService.findSheetsCell(code);
    }

    private void processFoundResult(FoundCellResult result) {

    }

    public void registerBarcodeObserver(ValueObserver<String> observer) {
        barCode.registerObserver(observer);
    }

    public void unregisterBarcodeObserver(ValueObserver<String> observer) {
        barCode.unregisterObserver(observer);
    }

    public void showOpenFileDialog() {
        logger.d(TAG, "showOpenFileDialog");
        for (MainServiceStateChangeListener listener : listeners) {
            listener.onOpenFileChooser();
        }
    }

    @Override
    public void onFoundSheetsCell(List<FoundCellResult> result) {
        StringBuilder sb = new StringBuilder();
        for (FoundCellResult res : result) {
            sb.append(res.toString());
            sb.append("; ");
        }
        for (MainServiceStateChangeListener listener : listeners) {
            listener.onFoundDocumentCellByAddress(sb.toString());
        }
    }
}
