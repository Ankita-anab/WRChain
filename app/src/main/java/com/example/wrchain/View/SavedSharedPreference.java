package com.example.wrchain.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SavedSharedPreference {
 private  static final String DATA_LOGIN= "status_login", USER_TYPE="usertype",EMAIL="email";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserType(Context ctx, String data)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_TYPE,data);
        editor.commit();
    }

    public static String getUserType(Context ctx)
    {
        return getSharedPreferences(ctx).getString(USER_TYPE, "");
    }
    public static void clearData(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(DATA_LOGIN);
        editor.remove(USER_TYPE);
        editor.remove(EMAIL);
        //clear all stored data
        editor.commit();
    }
    public  static void setDataLogin(Context ctx, boolean status)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(DATA_LOGIN,status);
        editor.commit();
    }

    public static boolean getDataLogin(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(DATA_LOGIN, false);
    }
    public  static void setEmail(Context ctx, String email)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(EMAIL,email);
        editor.commit();
    }

    public static String getEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString(EMAIL, "");
    }
}
