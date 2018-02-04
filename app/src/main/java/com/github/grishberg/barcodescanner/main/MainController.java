package com.github.grishberg.barcodescanner.main;

import com.github.grishberg.barcodescanner.common.ServiceListenerRegistrator;
import com.github.grishberg.barcodescanner.common.ValueObserver;

/**
 * Created by grishberg on 04.02.18.
 */

public interface MainController extends ServiceListenerRegistrator<MainServiceStateChangeListener> {

    void onScannerButtonClicked();

    void registerBarcodeObserver(ValueObserver<String> observer);

    void unregisterBarcodeObserver(ValueObserver<String> observer);

    void onOpenFileButtonClicked();
}
