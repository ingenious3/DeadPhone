package com.app.deadlauncher.data;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppsLoader extends AsyncTaskLoader<ArrayList<AppModel>> {
    ArrayList<AppModel> mInstalledApps;
    final PackageManager mPackageManager;

    public AppsLoader(Context context) {
        super(context);
        mPackageManager = context.getPackageManager();
        mInstalledApps = new ArrayList<>();
    }

    @Override
    public ArrayList<AppModel> loadInBackground() {
        // retrieve the list of installed applications
        List<ApplicationInfo> apps = mPackageManager.getInstalledApplications(0);

        if (apps == null) {
            apps = new ArrayList<ApplicationInfo>();
        }

        final Context context = getContext();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = mPackageManager.queryIntentActivities(mainIntent, 0);

        for (ResolveInfo resolveInfo : pkgAppsList) {
            AppModel appModel = new AppModel();
            appModel.setAppLabel(resolveInfo.loadLabel(mPackageManager).toString());
            appModel.setAppPackage(resolveInfo.activityInfo.packageName.toString());
            appModel.setIcon(resolveInfo.activityInfo.loadIcon(mPackageManager));
            mInstalledApps.add(appModel);
        }

        Collections.sort(mInstalledApps, ALPHA_COMPARATOR);
        return mInstalledApps;

    }

    @Override
    public void deliverResult(ArrayList<AppModel> apps) {
        if (isReset()) {
            if (apps != null) {
                onReleaseResources(apps);
            }
        }

        ArrayList<AppModel> oldApps = apps;
        mInstalledApps = apps;

        if (isStarted()) {
            super.deliverResult(apps);
        }

        if (oldApps != null) {
            onReleaseResources(oldApps);
        }
    }

    @Override
    protected void onStartLoading() {
//        if (mInstalledApps != null) {
//            deliverResult(mInstalledApps);
//        }

//        if (takeContentChanged() || mInstalledApps == null ) {
            forceLoad();
//        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(ArrayList<AppModel> apps) {
        super.onCanceled(apps);
        onReleaseResources(apps);
    }

    @Override
    protected void onReset() {
        onStopLoading();

        if (mInstalledApps != null) {
            onReleaseResources(mInstalledApps);
            mInstalledApps = null;
        }
    }

    protected void onReleaseResources(ArrayList<AppModel> apps) {

    }

    public static final Comparator<AppModel> ALPHA_COMPARATOR = new Comparator<AppModel>() {
        private final Collator sCollator = Collator.getInstance();
        @Override
        public int compare(AppModel object1, AppModel object2) {
            return sCollator.compare(object1.getAppLabel(), object2.getAppLabel());
        }
    };
}
