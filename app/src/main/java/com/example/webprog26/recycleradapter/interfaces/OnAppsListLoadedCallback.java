package com.example.webprog26.recycleradapter.interfaces;

import android.app.Application;
import android.content.pm.ResolveInfo;

import com.example.webprog26.recycleradapter.models.AppModel;

import java.util.List;

/**
 * Created by webpr on 17.01.2017.
 */

public interface OnAppsListLoadedCallback {
    public void onAppsListLoaded(List<AppModel> appModels);
}
