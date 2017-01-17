package com.example.webprog26.recycleradapter.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.webprog26.recycleradapter.models.AppCategorySaver;
import com.example.webprog26.recycleradapter.models.AppModel;

import java.lang.ref.WeakReference;

/**
 * Created by webpr on 17.01.2017.
 */

public class DbProvider {

    private static final String TAG = "DbProvider";

    private WeakReference<Context> mContextWeakReference;
    private DbHelper mDbHelper;

    public DbProvider(Context context) {
        mContextWeakReference = new WeakReference<Context>(context);
        mDbHelper = new DbHelper(mContextWeakReference.get());
    }

    public boolean isMatching(String appLabel){
        String whereClause = DbHelper.APP_NAME + " = ?";
        String[] whereArgs = new String[]{appLabel};

        long id = 0;

        Cursor cursor = mDbHelper.getReadableDatabase().query(DbHelper.TABLE_NAME, null, whereClause, whereArgs, null, null, null);

        while (cursor.moveToNext()){
            id = cursor.getLong(cursor.getColumnIndex(DbHelper.APP_ID));
        }

        return id != 0;
    }

    public AppCategorySaver getAppCategorySaver(String appLabel){

        AppCategorySaver appCategorySaver = new AppCategorySaver();

        String whereClause = DbHelper.APP_NAME + " = ?";
        String[] whereArgs = new String[]{appLabel};

        Cursor cursor = mDbHelper.getReadableDatabase().query(DbHelper.TABLE_NAME, null, whereClause, whereArgs, null, null, null);

        while (cursor.moveToNext()){
            appCategorySaver.setNeutral(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DbHelper.IS_NEUTRAL))));
            appCategorySaver.setEducational(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DbHelper.IS_EDUCATIONAL))));
            appCategorySaver.setForFun(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DbHelper.IS_FOR_FUN))));
            appCategorySaver.setBlocked(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DbHelper.IS_BLOCKED))));
        }

        return appCategorySaver;
    }

    public void saveAppCategory(AppModel model){
        Log.i(TAG, "saving to db " + model.toString());
        AppCategorySaver appCategorySaver = model.getAppCategorySaver();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.IS_NEUTRAL, String.valueOf(appCategorySaver.isNeutral()));
        contentValues.put(DbHelper.IS_EDUCATIONAL, String.valueOf(appCategorySaver.isEducational()));
        contentValues.put(DbHelper.IS_FOR_FUN, String.valueOf(appCategorySaver.isForFun()));
        contentValues.put(DbHelper.IS_BLOCKED, String.valueOf(appCategorySaver.isBlocked()));

        if(isMatching(model.getAppLabel())){
            String whereClause = DbHelper.APP_NAME + " = ?";
            String[] whereArgs = new String[]{model.getAppLabel()};

            mDbHelper.getWritableDatabase().update(DbHelper.TABLE_NAME, contentValues, whereClause, whereArgs);
            return;
        }
        contentValues.put(DbHelper.APP_NAME, model.getAppLabel());
        mDbHelper.getWritableDatabase().insert(DbHelper.TABLE_NAME, null, contentValues);
    }
}
