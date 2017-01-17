package com.example.webprog26.recycleradapter.threads;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.example.webprog26.recycleradapter.db.DbProvider;
import com.example.webprog26.recycleradapter.interfaces.OnAppsListLoadedCallback;
import com.example.webprog26.recycleradapter.managers.DrawableToBitmapConverter;
import com.example.webprog26.recycleradapter.models.AppModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by webpr on 17.01.2017.
 */

public class AppsListLoadingThread extends Thread {

    private static final String TAG = "AppsLoadingThread";

    private OnAppsListLoadedCallback mOnAppsListLoadedCallback;
    private PackageManager mPackageManager;
    private DbProvider mDbProvider;

    public AppsListLoadingThread(PackageManager packageManager, OnAppsListLoadedCallback mOnAppsListLoadedCallback, DbProvider dbProvider) {
        this.mPackageManager = packageManager;
        this.mOnAppsListLoadedCallback = mOnAppsListLoadedCallback;
        this.mDbProvider = dbProvider;
    }

    @Override
    public void run() {
        super.run();
        loadAppsList();
    }

    private void loadAppsList(){
        List<AppModel> appModels = new ArrayList<>();

        for(ResolveInfo resolveInfo: getLauncherActivitiesList(mPackageManager)){
            String appLabel = resolveInfo.loadLabel(mPackageManager).toString();
            AppModel model = new AppModel();
                model.setAppLabel(appLabel);
                if(mDbProvider.isMatching(appLabel)){
                    model.setWithCategory(true);
                    model.setAppCategorySaver(mDbProvider.getAppCategorySaver(appLabel));
                    Log.i(TAG, "loading from db " + model.toString());
                }
                model.setAppIcon(DrawableToBitmapConverter.drawableToBitmap(resolveInfo.loadIcon(mPackageManager)));
                appModels.add(model);
        }
        mOnAppsListLoadedCallback.onAppsListLoaded(appModels);
    }

    private List<ResolveInfo> getLauncherActivitiesList(final PackageManager packageManager){
        Intent getAppsIntent = new Intent(Intent.ACTION_MAIN);
        getAppsIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> activities = packageManager.queryIntentActivities(getAppsIntent, 0);

        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo a, ResolveInfo b) {
                return String.CASE_INSENSITIVE_ORDER
                        .compare(
                                a.loadLabel(packageManager).toString(),
                                b.loadLabel(packageManager).toString()
                        );
            }
        });

        return activities;
    }
}
