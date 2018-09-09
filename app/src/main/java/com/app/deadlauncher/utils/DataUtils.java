package com.app.deadlauncher.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.app.deadlauncher.data.AppMod;
import com.app.deadlauncher.data.AppModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class DataUtils {

    private static String sharedPrefFile = "com.app.deadlauncher";
    private static SharedPreferences mPreferences;

    public static void saveArrayList(ArrayList<AppMod> list, Context context){

        mPreferences = context.getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(list).toString();
        editor.putString("apps", json);
        editor.putInt("count",list.size());
        editor.apply();
    }

    public static  ArrayList<AppMod> getArrayList(Context context){
        mPreferences = context.getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPreferences.getString("apps", "");
        Type type = new TypeToken<ArrayList<AppMod>>() {}.getType();
        return gson.fromJson(json, type);
    }


}
