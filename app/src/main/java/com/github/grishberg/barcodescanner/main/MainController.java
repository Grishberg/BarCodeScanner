package com.github.grishberg.barcodescanner.main;

import com.github.grishberg.barcodescanner.common.ServiceListenerRegistrator;
import com.github.grishberg.barcodescanner.common.ValueObserver;
import com.github.grishberg.barcodescanner.form.CellRelation;

import java.util.List;

/**
 * Created by grishberg on 04.02.18.
 */

public interface MainController extends ServiceListenerRegistrator<MainServiceStateChangeListener> {

    void registerBarcodeObserver(ValueObserver<String> observer);

    void unregisterBarcodeObserver(ValueObserver<String> observer);

    void registerResultCellsObserver(ValueObserver<List<CellRelation>> observer);

    void unregisterResultCellsObserver(ValueObserver<List<CellRelation>> observer);

    void onOpenFileButtonClicked();

    void openDocument(String path);
}
