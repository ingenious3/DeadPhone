package com.app.deadlauncher.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.deadlauncher.R;

public class HomeAppAdapter extends RecyclerView.Adapter<HomeAppListViewHolder> {
    @NonNull
    @Override
    public HomeAppListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item,parent,false);
        return (new HomeAppListViewHolder(v));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAppListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
