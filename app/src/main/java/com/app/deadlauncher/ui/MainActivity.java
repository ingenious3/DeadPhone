package com.app.deadlauncher.ui;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.deadlauncher.R;
import com.app.deadlauncher.data.AppModel;
import com.app.deadlauncher.data.AppsLoader;

import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener, View.OnLongClickListener,  LoaderManager.LoaderCallbacks<ArrayList<AppModel>> {

    private TextView textBrowser, textCall, textMail, textMessages, textLauncher, textSettings, textSetApp;

    private int APP_REQUEST_CODE = 1;

    PackageManager mPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPm = getPackageManager();
//        List<ApplicationInfo> apps = mPm.getInstalledApplications(PackageManager.MATCH_SYSTEM_ONLY);

//        for(int i=0; i<apps.size() ; i++) {
//            Log.e("MyTAG",apps.get(i).packageName + " "+apps.get(i).name);
//        }

//        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
//        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//        List<ResolveInfo> pkgAppsList = getPackageManager().queryIntentActivities( mainIntent, 0);
//
//        for(int i=0; i<pkgAppsList.size() ; i++) {
//            Log.e("MyTAG",pkgAppsList.get(i).loadLabel(mPm).toString() + " -> "+pkgAppsList.get(i).activityInfo.packageName+ " -> "+pkgAppsList.get(i).activityInfo.loadIcon(mPm));
//        }
//
//        getPackages();

        getLoaderManager().initLoader(0, null, this);


        textBrowser = (TextView)findViewById(R.id.textBrowser);
        textCall = (TextView)findViewById(R.id.textCall);
        textMail = (TextView)findViewById(R.id.textMail);
        textMessages = (TextView)findViewById(R.id.textMessages);
        textLauncher = (TextView)findViewById(R.id.textLauncher);
        textSettings = (TextView)findViewById(R.id.textSettings);
        textSetApp = (TextView)findViewById(R.id.textSetApp);

        textBrowser.setOnClickListener(this);
        textCall.setOnClickListener(this);
        textMail.setOnClickListener(this);
        textMessages.setOnClickListener(this);
        textLauncher.setOnClickListener(this);
        textSettings.setOnClickListener(this);
        textSetApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textBrowser:
                Intent browserIntent =  getPackageManager().getLaunchIntentForPackage("com.android.chrome");
                if (browserIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(browserIntent,APP_REQUEST_CODE);
                } else {
                    Toast.makeText(this,"No app can perform the action. Please install an app",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.textCall:
//                Intent callIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.dialer");
//                if(callIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(callIntent, APP_REQUEST_CODE);
//                } else {
//                    Toast.makeText(this,"No app can perform the action. Please install an app",Toast.LENGTH_SHORT).show();
//                }

                Intent i = mPm.getLaunchIntentForPackage("com.android.dialer");
                startActivity(i);
                break;
            case R.id.textMail:
                Intent mailIntent =  getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
//                mailIntent.setClassName("com.google.android.gm", "com.google.android.gm.*");
//                Intent mailIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:")); "com.android.chrome"
                if(mailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(mailIntent, APP_REQUEST_CODE);
                } else {
                    Toast.makeText(this,"No app can perform the action. Please install an app",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.textMessages:
                Intent sendIntent = getPackageManager().getLaunchIntentForPackage("com.microsoft.android.smsorganizer");
                if(sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(sendIntent, APP_REQUEST_CODE);
                } else {
                    Toast.makeText(this,"No app can perform the action. Please install an app",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.textLauncher:
                Intent homeIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
//                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
//                    homeIntent = new Intent(Settings.ACTION_SETTINGS);
//                }
//                Intent homeIntent = new Intent();
//                homeIntent.setPackage("com.whatsapp");
//                homeIntent.setData(Uri.parse("whatsapp://send"));
//                startActivity(homeIntent);
                if(homeIntent!=null && homeIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(homeIntent, APP_REQUEST_CODE);
                } else {
                    Toast.makeText(this,"No app can perform the action. Please install an app",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.textSettings:
                Intent settingsIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                if(settingsIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(settingsIntent,APP_REQUEST_CODE);
                } else {
                    Toast.makeText(this,"No app can perform the action. Please install an app",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.textSetApp:
                Intent setappIntent = new Intent(this,ListAppActivity.class);
                startActivity(setappIntent);

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
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY ;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public Loader<ArrayList<AppModel>> onCreateLoader(int id, Bundle args) {
        return new AppsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<AppModel>> loader, ArrayList<AppModel> data) {
        for (AppModel d : data) {
            Log.e("MyTAG",d.getAppLabel());
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<AppModel>> loader) {

    }

//    class PInfo {
//        private String appname = "";
//        private String pname = "";
//        private String versionName = "";
//        private int versionCode = 0;
//        private Drawable icon;
//        private void prettyPrint() {
//            Log.v("MTAG2",appname + "\t" + pname + "\t" + versionName + "\t" + versionCode);
//        }
//    }

//    private ArrayList<PInfo> getPackages() {
//        ArrayList<PInfo> apps = getInstalledApps(false); /* false = no system packages */
//        final int max = apps.size();
//        for (int i=0; i<max; i++) {
//            apps.get(i).prettyPrint();
//        }
//        return apps;
//    }


//    private ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {
//        ArrayList<PInfo> res = new ArrayList<PInfo>();
//        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
//        for(int i=0;i<packs.size();i++) {
//            PackageInfo p = packs.get(i);
//            if ((!getSysPackages) && (p.versionName == null)) {
//                continue ;
//            }
//            PInfo newInfo = new PInfo();
//            newInfo.appname = p.applicationInfo.loadLabel(getPackageManager()).toString();
//            newInfo.pname = p.packageName;
//            newInfo.versionName = p.versionName;
//            newInfo.versionCode = p.versionCode;
//            newInfo.icon = p.applicationInfo.loadIcon(getPackageManager());
//            res.add(newInfo);
//        }
//        return res;
//    }

}
