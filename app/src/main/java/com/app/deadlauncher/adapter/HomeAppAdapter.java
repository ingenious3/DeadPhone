package com.app.deadlauncher.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.deadlauncher.R;
import com.app.deadlauncher.adapter.viewholder.HomeAppListViewHolder;
import com.app.deadlauncher.data.AppMod;
import com.app.deadlauncher.data.AppModel;

import java.util.ArrayList;

public class HomeAppAdapter extends RecyclerView.Adapter<HomeAppListViewHolder> {

    private ArrayList<AppMod> appsList = new ArrayList<>();
    private Context context ;

    public HomeAppAdapter(ArrayList<AppMod> appsList, Context context) {
        this.appsList = appsList;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeAppListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item,parent,false);
        return (new HomeAppListViewHolder(v));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAppListViewHolder holder, int position) {
        AppMod appModel = appsList.get(position);
        holder.appTitle.setText(appModel.getAppLabel());
    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }
}
