package com.app.deadlauncher.ui;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.deadlauncher.R;
import com.app.deadlauncher.adapter.HomeAppAdapter;
import com.app.deadlauncher.adapter.ListAppAdapter;
import com.app.deadlauncher.data.AppMod;
import com.app.deadlauncher.data.AppModel;
import com.app.deadlauncher.data.AppsLoader;
import com.app.deadlauncher.utils.DataUtils;
import com.app.deadlauncher.utils.RecyclerItemClickListener;

import java.util.ArrayList;

public class ListAppActivity extends Activity implements  LoaderManager.LoaderCallbacks<ArrayList<AppModel>> {

    private Context context ;
    private RecyclerView rvApps;
    private ArrayList<AppModel> appList;
    private ListAppAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_app);
        getLoaderManager().initLoader(0, null, this);

        rvApps = (RecyclerView)findViewById(R.id.rvListApp);
        rvApps.setHasFixedSize(true);
        rvApps.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvApps.addOnItemTouchListener(new RecyclerItemClickListener(ListAppActivity.this, rvApps ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        ArrayList<AppMod> apps = DataUtils.getArrayList(getApplicationContext());

                        if( apps == null ) {
                            apps = new ArrayList<>();
                        }
                        if ( apps.size()<5) {
                            AppMod appMod = new AppMod();
                            appMod.setAppLabel(appList.get(position).getAppLabel());
                            appMod.setAppPackage(appList.get(position).getAppPackage());
                            apps.add(appMod);
                            DataUtils.saveArrayList(apps,getApplicationContext());
                            ListAppActivity.this.finish();
                        } else {
                            Toast.makeText(ListAppActivity.this,"You've already selected five apps. Remove app from list and then add. ",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override public void onLongItemClick(View view, int position) {
                    }
                })
        );
    }

    @Override
    public Loader<ArrayList<AppModel>> onCreateLoader(int id, Bundle args) {
        return new AppsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<AppModel>> loader, ArrayList<AppModel> data) {

        for(AppModel d : data) {
            Log.e("MyTAG",d.getAppPackage() + " - ");
        }


        ArrayList<AppMod> myApps = DataUtils.getArrayList(getApplicationContext());
        if( myApps == null) {
            myApps = new ArrayList<>();
        }
        ArrayList<AppModel> tempApps = new ArrayList<>();
        for(AppModel appModel : data) {
            for(AppMod appMod : myApps) {
                if(appModel.getAppPackage().equalsIgnoreCase(appMod.getAppPackage())) {
                    tempApps.add(appModel);
                }
            }
            if (appModel.getAppPackage().equalsIgnoreCase("com.app.deadlauncher")) {
                tempApps.add(appModel);
            }
        }

        appList = data;
        for(AppModel appModel : tempApps) {
            appList.remove(appModel);
        }
        setupList(appList);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<AppModel>> loader) {

    }

    public void setupList(ArrayList<AppModel> appList){
        if( appList != null ) {
            adapter = new ListAppAdapter(appList,ListAppActivity.this);
            adapter.notifyDataSetChanged();
            rvApps.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
