package com.app.deadlauncher.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.deadlauncher.R;
import com.app.deadlauncher.adapter.viewholder.AppListViewHolder;
import com.app.deadlauncher.data.AppModel;

import java.util.ArrayList;


public class ListAppAdapter extends RecyclerView.Adapter<AppListViewHolder>{

    private ArrayList<AppModel> appsList = new ArrayList<>();
    private Context context ;

    public ListAppAdapter(ArrayList<AppModel> appsList, Context context) {
        this.appsList = appsList;
        this.context = context;
    }

    @NonNull
    @Override
    public AppListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_item,parent,false);
        return (new AppListViewHolder(v));
    }

    @Override
    public void onBindViewHolder(@NonNull AppListViewHolder holder, int position) {
        AppModel appModel = appsList.get(position);
        holder.appLabel.setText(appModel.getAppLabel());
        holder.appIcon.setImageDrawable(appModel.getIcon());
    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }
}
