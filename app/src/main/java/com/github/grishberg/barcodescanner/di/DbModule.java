package com.github.grishberg.barcodescanner.di;

import android.content.Context;

import com.github.grishberg.barcodescanner.form.CellRelationDao;
import com.github.grishberg.barcodescanner.form.DaoMaster;
import com.github.grishberg.barcodescanner.form.DaoSession;

import org.greenrobot.greendao.database.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by grishberg on 12.02.18.
 */
@Module
public class DbModule {
    @Singleton
    @Provides
    CellRelationDao provideRelationsDao(Context appContext) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(appContext, "relations-db");
        Database db = helper.getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();

        return daoSession.getCellRelationDao();
    }
}
