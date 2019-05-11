package com.app.deadlauncher.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.app.deadlauncher.R;

import com.app.deadlauncher.adapter.HomeAppAdapter;
import com.app.deadlauncher.data.AppMod;
import com.app.deadlauncher.utils.DataUtils;
import com.app.deadlauncher.utils.RecyclerItemClickListener;

import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener{

    private TextView textSettings, textSetApp, textMsg;
    private RecyclerView rvMyApps;
    private int APP_REQUEST_CODE = 1;
    private ArrayList<AppMod> appList;
    private HomeAppAdapter adapter;
    private CheckBox themeCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppTheme();
        setContentView(R.layout.activity_main);

        textSettings = (TextView)findViewById(R.id.textSettings);
        textSetApp = (TextView)findViewById(R.id.textSetApp);
        textMsg = (TextView)findViewById(R.id.textMessage);
        themeCheckBox = (CheckBox)findViewById(R.id.themeCheckBox);

        textSettings.setOnClickListener(this);
        textSetApp.setOnClickListener(this);
        themeCheckBox.setOnClickListener(this);

        msgShowHide();

        rvMyApps = (RecyclerView)findViewById(R.id.rvMyApps);
        rvMyApps.setHasFixedSize(true);
        rvMyApps.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvMyApps.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, rvMyApps ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        AppMod data = appList.get(position);
                        Intent intent =  getPackageManager().getLaunchIntentForPackage(data.getAppPackage());
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent,APP_REQUEST_CODE);
                        } else {

                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                    }
                })
        );

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeApp(appList.get(viewHolder.getAdapterPosition()));
                msgShowHide();
            }
        }).attachToRecyclerView(rvMyApps);
    }

    private void msgShowHide() {
        if(!DataUtils.getStringVal(getApplicationContext(),"count").equalsIgnoreCase("")
                && Integer.parseInt(DataUtils.getStringVal(getApplicationContext(),"count"))>0) {
            textMsg.setVisibility(View.GONE);
        } else {
            textMsg.setVisibility(View.VISIBLE);
        }
    }

    private void setAppTheme() {
        if(DataUtils.getStringVal(getApplicationContext(),"theme") == null || DataUtils.getStringVal(getApplicationContext(), "theme").equalsIgnoreCase("")
                || DataUtils.getStringVal(getApplicationContext(), "theme").equalsIgnoreCase("dark")) {
            setTheme(R.style.DarkAppTheme);
        } else if(DataUtils.getStringVal(getApplicationContext(), "theme").equalsIgnoreCase("light")) {
            setTheme(R.style.LightAppTheme);
        }
    }

    public void removeApp(AppMod data) {
        appList.remove(data);
        adapter.notifyDataSetChanged();
        DataUtils.saveArrayList(appList,getApplicationContext());
        DataUtils.setStringVal(getApplicationContext(),"count",appList.size()+"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textSettings:
                Intent settingsIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                if(settingsIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(settingsIntent,APP_REQUEST_CODE);
                } else {
                    //Toast.makeText(this,"No app can perform the action. Please install an app",Toast.LENGTH_SHORT).show();
                    showToast("No app can perform the action. Please install an app");
                }
                break;
            case R.id.textSetApp:
                if(!DataUtils.getStringVal(getApplicationContext(),"count").equalsIgnoreCase("")
                    && Integer.parseInt(DataUtils.getStringVal(getApplicationContext(),"count")) == 5) {
//                    Toast.makeText(MainActivity.this,"You've already selected five apps. Remove app from list and then add. ",Toast.LENGTH_SHORT).show();
                    showToast("You've already selected five apps. Remove app from list and then add.");
                    return;
                }
                Intent setappIntent = new Intent(this,ListAppActivity.class);
                startActivity(setappIntent);
                break;
            case R.id.themeCheckBox:
                if(themeCheckBox.isChecked()) {
                    DataUtils.setStringVal(getApplicationContext(),"theme","light");
                } else {
                    DataUtils.setStringVal(getApplicationContext(),"theme","dark");
                }
                setAppTheme();
                recreate();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == APP_REQUEST_CODE) {
            if(requestCode == RESULT_CANCELED || resultCode == RESULT_OK || resultCode == RESULT_FIRST_USER ) {

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        msgShowHide();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY ;
        decorView.setSystemUiVisibility(uiOptions);

        appList = DataUtils.getArrayList(getApplicationContext());
        if( appList != null ) {
            adapter = new HomeAppAdapter(appList,MainActivity.this);
            adapter.notifyDataSetChanged();
            rvMyApps.setAdapter(adapter);
        }
    }

    private void showToast(String message) {
        LayoutInflater inflater = this.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) this.findViewById(R.id.root));
        TextView text = (TextView) layout.findViewById(R.id.customToastText);
        text.setText(message);
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void onBackPressed() {

    }

}
