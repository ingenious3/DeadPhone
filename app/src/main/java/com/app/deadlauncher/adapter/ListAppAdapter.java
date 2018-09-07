package com.app.deadlauncher.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.deadlauncher.R;
import com.app.deadlauncher.adapter.viewholder.AppListViewHolder;


public class ListAppAdapter extends RecyclerView.Adapter<AppListViewHolder>{


    @NonNull
    @Override
    public com.app.deadlauncher.adapter.viewholder.AppListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_item,parent,false);
        return (new com.app.deadlauncher.adapter.viewholder.AppListViewHolder(v));
    }

    @Override
    public void onBindViewHolder(@NonNull com.app.deadlauncher.adapter.viewholder.AppListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
