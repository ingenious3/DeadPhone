package com.app.deadlauncher.data;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AppModel {

    @SerializedName("appLabel")
    @Expose
    String appLabel;

    @SerializedName("appPackage")
    @Expose
    String appPackage;

    @SerializedName("icon")
    @Expose
    Drawable icon;

    public AppModel() {
        appLabel = "";
        appPackage = "";
        icon = null;
    }

    public String getAppLabel() {
        return appLabel;
    }

    public void setAppLabel(String appLabel) {
        this.appLabel = appLabel;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
