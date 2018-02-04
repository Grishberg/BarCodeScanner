package com.github.grishberg.barcodescanner.di;

/**
 * Created by grishberg on 04.02.18.
 */
public class DiManager {
    private static AppComponent appComponent;

    public static void initComponents(final AppComponent component) {
        appComponent = component;
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
