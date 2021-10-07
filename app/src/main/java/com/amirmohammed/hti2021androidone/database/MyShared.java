package com.amirmohammed.hti2021androidone.database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class MyShared {

    private static SharedPreferences mSharedPref;

    private MyShared()
    {

    }

    public static void init(Context context)
    {
        if(mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static String read(Keys key, String defValue) {
        return mSharedPref.getString(key.toString() , defValue);
    }

    public static void write(Keys key, String value) {
        mSharedPref.edit().putString(key.toString(), value).apply();
    }

    public static boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    public static Integer read(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    public static void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).apply();
    }

}
