package com.example.webprog26.recycleradapter.listeners;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.webprog26.recycleradapter.R;
import com.example.webprog26.recycleradapter.db.DbProvider;
import com.example.webprog26.recycleradapter.managers.AppCategoryManager;
import com.example.webprog26.recycleradapter.models.AppCategorySaver;
import com.example.webprog26.recycleradapter.models.AppModel;
import com.example.webprog26.recycleradapter.threads.AppCategorySavingThread;

import java.lang.ref.WeakReference;

/**
 * Created by webpr on 17.01.2017.
 */

public class OnRadioButtonClickListener implements View.OnClickListener {

    private static final String TAG = "RadioButtonListener";

    private AppModel mAppModel;
    private AppCategorySaver mAppCategorySaver;
    private DbProvider mDbProvider;
    private TextView mAppCategoryTextView;
    private WeakReference<Context> mContextWeakReference;

    public OnRadioButtonClickListener(AppModel appModel, DbProvider dbProvider, TextView appCategoryTextView, Context context) {
        this.mAppModel = appModel;
        this.mAppCategorySaver = mAppModel.getAppCategorySaver();
        this.mDbProvider = dbProvider;
        this.mAppCategoryTextView = appCategoryTextView;
        this.mContextWeakReference = new WeakReference<Context>(context);
    }

    @Override
    public void onClick(View v) {
        AppCompatRadioButton radioButton = (AppCompatRadioButton) v;

        switch (radioButton.getId()){
            case R.id.rbEducational:
                if(mAppCategorySaver.isEducational()){
                    mAppCategorySaver.setNeutral(true);
                    radioButton.setChecked(false);
                    break;
                }
                mAppCategorySaver.setNeutral(false);
                mAppCategorySaver.setEducational(true);
                mAppCategorySaver.setForFun(false);
                mAppCategorySaver.setBlocked(false);
                break;
            case R.id.rbForFun:
                if(mAppCategorySaver.isForFun()){
                    mAppCategorySaver.setNeutral(true);
                    radioButton.setChecked(false);
                    break;
                }
                mAppCategorySaver.setNeutral(false);
                mAppCategorySaver.setEducational(false);
                mAppCategorySaver.setForFun(true);
                mAppCategorySaver.setBlocked(false);
                break;
            case R.id.rbBlocked:
                if(mAppCategorySaver.isBlocked()){
                    mAppCategorySaver.setNeutral(true);
                    radioButton.setChecked(false);
                    break;
                }
                mAppCategorySaver.setNeutral(false);
                mAppCategorySaver.setEducational(false);
                mAppCategorySaver.setForFun(false);
                mAppCategorySaver.setBlocked(true);
                break;
        }
        Log.i(TAG, mAppModel.toString());
        new AppCategorySavingThread(mAppModel, mDbProvider).start();
        new AppCategoryManager(mContextWeakReference.get()).setAppCategory(mAppCategorySaver, mAppCategoryTextView);
    }
}
