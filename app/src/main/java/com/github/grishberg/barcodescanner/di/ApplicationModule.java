package com.github.grishberg.barcodescanner.di;

import android.content.Context;

import com.github.grishberg.barcodescanner.common.LogTagLogger;
import com.github.grishberg.barcodescanner.common.Logger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by grishberg on 04.02.18.
 */
@Module
public class ApplicationModule {

    private final Context appContext;

    public ApplicationModule(Context appContext) {
        this.appContext = appContext;
    }

    @Provides
    Context provideContext() {
        return appContext;
    }

    @Singleton
    @Provides
    Logger provideLogger() {
        return new LogTagLogger();
    }
}

