package com.github.grishberg.barcodescanner;

import android.app.Application;
import android.os.StrictMode;

import com.github.grishberg.barcodescanner.di.ApplicationModule;
import com.github.grishberg.barcodescanner.di.DaggerAppComponent;
import com.github.grishberg.barcodescanner.di.DiManager;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by grishberg on 04.02.18.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initStrictMode();

        //initLeakCanary();

        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");

        DiManager.initComponents(DaggerAppComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build()
        );
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    private void initStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }
}
