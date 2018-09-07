package com.app.deadlauncher.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.deadlauncher.R;

public class HomeAppListViewHolder extends RecyclerView.ViewHolder {

    public TextView appTitle;

    public HomeAppListViewHolder(View itemView) {
        super(itemView);
        appTitle = (TextView)itemView.findViewById(R.id.appTitleHome);
    }
}
