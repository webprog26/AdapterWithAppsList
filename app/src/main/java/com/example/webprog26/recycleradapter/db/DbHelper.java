package com.example.webprog26.recycleradapter.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by webpr on 17.01.2017.
 */

public class DbHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "task_adapter_apps_db";
    private static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static final String TABLE_NAME = "task_adapter_apps_table";
    public static final String APP_ID = "_id";
    public static final String APP_NAME = "app_name";
    public static final String IS_NEUTRAL = "is_neutral";
    public static final String IS_EDUCATIONAL = "is_educational";
    public static final String IS_FOR_FUN = "is_for_fun";
    public static final String IS_BLOCKED = "is_blocked";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "("
                + APP_ID + " integer primary key autoincrement, "
                + APP_NAME + " varchar(200), "
                + IS_BLOCKED + " varchar(20), "
                + IS_EDUCATIONAL + " varchar(20), "
                + IS_FOR_FUN + " varchar(20), "
                + IS_NEUTRAL  + " varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }
}
