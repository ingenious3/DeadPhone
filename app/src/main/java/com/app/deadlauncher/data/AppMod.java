package com.app.deadlauncher.data;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppMod {
    @SerializedName("appLabel")
    @Expose
    String appLabel;

    @SerializedName("appPackage")
    @Expose
    String appPackage;

    public AppMod() {
        appLabel = "";
        appPackage = "";
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
}
