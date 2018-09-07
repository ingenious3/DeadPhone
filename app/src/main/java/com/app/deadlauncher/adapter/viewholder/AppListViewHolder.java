package com.app.deadlauncher.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.deadlauncher.R;

public class AppListViewHolder extends RecyclerView.ViewHolder {

    public TextView appLabel;
    public ImageView appIcon;

    public AppListViewHolder(View itemView) {
        super(itemView);
        appIcon = (ImageView)itemView.findViewById(R.id.appIconList);
        appLabel = (TextView)itemView.findViewById(R.id.appTitleList);
    }
}
