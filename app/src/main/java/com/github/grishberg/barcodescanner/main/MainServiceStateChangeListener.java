package com.github.grishberg.barcodescanner.main;

/**
 * Created by grishberg on 04.02.18.
 */

public interface MainServiceStateChangeListener {
    void onShowBarCodeScanner();

    void onOpenFileChooser();

    void onShowMainScreen();

    void onShowAddRelationsScreen();
}
