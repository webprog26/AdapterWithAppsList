package com.example.webprog26.recycleradapter.managers;

import android.content.Context;
import android.widget.TextView;

import com.example.webprog26.recycleradapter.R;
import com.example.webprog26.recycleradapter.models.AppCategorySaver;

import java.lang.ref.WeakReference;

/**
 * Created by webpr on 17.01.2017.
 */

public class AppCategoryManager {

    private WeakReference<Context> mContextWeakReference;

    public AppCategoryManager(Context context) {
        mContextWeakReference = new WeakReference<Context>(context);
    }

    public void setAppCategory(AppCategorySaver appCategorySaver, TextView appCategoryTextView){
        if(appCategorySaver.isNeutral()){
            appCategoryTextView.setText(mContextWeakReference.get().getResources().getString(R.string.category_neutral));
            return;
        }

        if(appCategorySaver.isEducational()){
            appCategoryTextView.setText(mContextWeakReference.get().getResources().getString(R.string.category_education));
        } else if(appCategorySaver.isForFun()){
            appCategoryTextView.setText(mContextWeakReference.get().getResources().getString(R.string.category_fun));
        } else if(appCategorySaver.isBlocked()){
            appCategoryTextView.setText(mContextWeakReference.get().getResources().getString(R.string.category_blocked));
        }
    }
}
