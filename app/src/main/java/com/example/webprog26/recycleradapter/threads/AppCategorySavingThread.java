package com.example.webprog26.recycleradapter.threads;

import com.example.webprog26.recycleradapter.db.DbProvider;
import com.example.webprog26.recycleradapter.models.AppModel;

/**
 * Created by webpr on 17.01.2017.
 */

public class AppCategorySavingThread extends Thread {

    private AppModel mAppModel;
    private DbProvider mDbProvider;

    public AppCategorySavingThread(AppModel mAppModel, DbProvider dbProvider) {
        this.mAppModel = mAppModel;
        this.mDbProvider = dbProvider;
    }

    @Override
    public void run() {
        super.run();
        saveAppToDb();
    }

    private void saveAppToDb(){
        mDbProvider.saveAppCategory(mAppModel);
    }

}
