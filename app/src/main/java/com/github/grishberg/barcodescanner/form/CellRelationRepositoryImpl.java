package com.github.grishberg.barcodescanner.form;

import android.os.AsyncTask;

import com.github.grishberg.barcodescanner.common.Logger;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by grishberg on 07.02.18.
 */
public class CellRelationRepositoryImpl implements CellRelationRepository {
    private static final String TAG = CellRelationRepositoryImpl.class.getSimpleName();
    private final Logger logger;
    private String currentDocPath;
    private final CellRelationDao cellRelationDao;

    @Inject
    public CellRelationRepositoryImpl(Logger logger, CellRelationDao cellRelationDao) {
        this.logger = logger;
        this.cellRelationDao = cellRelationDao;
    }

    //TODO: add listener field to avoid memory leak.
    @Override
    public void findRepresentation(final String filePath, final OnRepresentationLoadedListener listener) {
        new AsyncTask<Void, Void, List<CellRelation>>() {
            @Override
            protected List<CellRelation> doInBackground(Void... voids) {
                Query<CellRelation> query = cellRelationDao
                        .queryBuilder()
                        .where(CellRelationDao.Properties.DocPath.eq(filePath))
                        .build();
                return query.list();
            }

            @Override
            protected void onPostExecute(List<CellRelation> relations) {
                if (listener != null) {
                    listener.onRepresentationLoaded(relations);
                }
            }
        }.execute();
    }

    @Override
    public void storeRelations(String filePath, List<CellRelation> relations) {
        for (CellRelation relation : relations) {
            relation.setDocPath(currentDocPath);
            cellRelationDao.insert(relation);
        }
    }

    @Override
    public void setCurrentDocumentName(String filePath) {
        currentDocPath = filePath;
    }

    @Override
    public void addRelationForCurrentDoc(CellRelation relation) {
        relation.setDocPath(currentDocPath);
        cellRelationDao.insert(relation);
    }
}
