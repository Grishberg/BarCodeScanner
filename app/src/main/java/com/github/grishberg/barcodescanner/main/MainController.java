package com.github.grishberg.barcodescanner.main;

/**
 * Created by grishberg on 04.02.18.
 */

public interface MainController {

    void onOpenFileButtonClicked();

    void openDocument(String path);

    void onCreated();
}
