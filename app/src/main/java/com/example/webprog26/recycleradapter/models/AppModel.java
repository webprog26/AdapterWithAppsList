package com.example.webprog26.recycleradapter.models;

import android.graphics.Bitmap;

/**
 * Created by webpr on 17.01.2017.
 */

public class AppModel {

    private String mAppLabel;
    private Bitmap mAppIcon;
    private AppCategorySaver mAppCategorySaver;
    private boolean withCategory;

    public AppModel() {
        this.mAppCategorySaver = new AppCategorySaver();
        withCategory = false;
    }

    public String getAppLabel() {
        return mAppLabel;
    }

    public void setAppLabel(String mAppLabel) {
        this.mAppLabel = mAppLabel;
    }

    public Bitmap getAppIcon() {
        return mAppIcon;
    }

    public void setAppIcon(Bitmap mAppIcon) {
        this.mAppIcon = mAppIcon;
    }

    public AppCategorySaver getAppCategorySaver() {
        return mAppCategorySaver;
    }

    public void setAppCategorySaver(AppCategorySaver mAppCategorySaver) {
        this.mAppCategorySaver = mAppCategorySaver;
    }

    public boolean isWithCategory() {
        return withCategory;
    }

    public void setWithCategory(boolean withCategory) {
        this.withCategory = withCategory;
    }

    @Override
    public String toString() {
        return "App " + getAppLabel() + " now has \n"
                + "neutral: " + mAppCategorySaver.isNeutral() + "\n"
                + "educational: " + mAppCategorySaver.isEducational() + "\n"
                + "for fun: " + mAppCategorySaver.isForFun() + "\n"
                + "blocked: " + mAppCategorySaver.isBlocked();
    }
}
