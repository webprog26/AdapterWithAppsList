package com.example.webprog26.recycleradapter.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.webprog26.recycleradapter.R;
import com.example.webprog26.recycleradapter.db.DbProvider;
import com.example.webprog26.recycleradapter.listeners.OnRadioButtonClickListener;
import com.example.webprog26.recycleradapter.models.AppCategorySaver;
import com.example.webprog26.recycleradapter.models.AppModel;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by webpr on 17.01.2017.
 */

public class AppsRecyclerViewAdapter extends RecyclerView.Adapter<AppsRecyclerViewAdapter.AppsRecyclerViewHolder> {

    private WeakReference<Context> mContextWeakReference;
    private List<AppModel> mAppModelList;
    private DbProvider mDbProvider;

    public AppsRecyclerViewAdapter(Context context, List<AppModel> mAppModelList, DbProvider dbProvider) {
        this.mContextWeakReference = new WeakReference<Context>(context);
        this.mAppModelList = mAppModelList;
        this.mDbProvider = dbProvider;

    }

    @Override
    public AppsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContextWeakReference.get()).inflate(R.layout.app_item_layout, null);
        return new AppsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppsRecyclerViewHolder holder, int position) {

        AppModel model = mAppModelList.get(position);

        holder.mIvAppIcon.setImageBitmap(model.getAppIcon());
        holder.mAppLabel.setText(model.getAppLabel());


        OnRadioButtonClickListener clickListener = new OnRadioButtonClickListener(model, mDbProvider);

        if(model.isWithCategory()){
            AppCategorySaver appCategorySaver = model.getAppCategorySaver();

            holder.mRbEducational.setChecked(appCategorySaver.isEducational());
            holder.mRbForFun.setChecked(appCategorySaver.isForFun());
            holder.mRbBlocked.setChecked(appCategorySaver.isBlocked());
        }

        holder.mRbEducational.setOnClickListener(clickListener);
        holder.mRbForFun.setOnClickListener(clickListener);
        holder.mRbBlocked.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return mAppModelList.size();
    }

    public class AppsRecyclerViewHolder extends RecyclerView.ViewHolder{

        private ImageView mIvAppIcon;
        private TextView mAppLabel;
        private AppCompatRadioButton mRbEducational, mRbForFun, mRbBlocked;

        public AppsRecyclerViewHolder(View itemView) {
            super(itemView);

            mIvAppIcon = (ImageView) itemView.findViewById(R.id.ivAppIcon);
            mAppLabel = (TextView) itemView.findViewById(R.id.tvAppLabel);

            mRbEducational = (AppCompatRadioButton) itemView.findViewById(R.id.rbEducational);
            mRbForFun = (AppCompatRadioButton) itemView.findViewById(R.id.rbForFun);
            mRbBlocked = (AppCompatRadioButton) itemView.findViewById(R.id.rbBlocked);
         }
    }
}
