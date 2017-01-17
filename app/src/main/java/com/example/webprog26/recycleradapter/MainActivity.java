package com.example.webprog26.recycleradapter;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.webprog26.recycleradapter.adapters.AppsRecyclerViewAdapter;
import com.example.webprog26.recycleradapter.db.DbProvider;
import com.example.webprog26.recycleradapter.interfaces.OnAppsListLoadedCallback;
import com.example.webprog26.recycleradapter.models.AppModel;
import com.example.webprog26.recycleradapter.threads.AppsListLoadingThread;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnAppsListLoadedCallback{

    private static final String TAG = "MainActivity_TAG";

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private AppsRecyclerViewAdapter mAdapter;
    private DbProvider mDbProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbProvider = new DbProvider(getApplicationContext());

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        PackageManager packageManager = getPackageManager();
        new AppsListLoadingThread(packageManager, this, mDbProvider).start();
    }

    @Override
    public void onAppsListLoaded(List<AppModel> appModels) {
        mAdapter = new AppsRecyclerViewAdapter(MainActivity.this, appModels, mDbProvider);

        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.post(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setVisibility(View.GONE);
                }
            });
        }

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setItemViewCacheSize(mAdapter.getItemCount());
                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }
}
