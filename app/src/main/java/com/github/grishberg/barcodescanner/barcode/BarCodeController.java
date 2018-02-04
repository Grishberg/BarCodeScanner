package com.github.grishberg.barcodescanner.barcode;

import com.github.grishberg.barcodescanner.common.ServiceListenerRegistrator;

/**
 * Created by grishberg on 03.02.18.
 */
public interface BarCodeController extends ServiceListenerRegistrator<ScannerServiceListener> {
    void onBarCodeFound(String code);
}
