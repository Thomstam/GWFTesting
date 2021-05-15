package com.example.gwftesting.measurements;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenSaver {

    public static final String SHARED_PREF_ACCESS = "accessPreference";
    public static final String SHARED_PREF_REFRESH = "refreshPreference";

    public static void setSharedPrefAccess(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_ACCESS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("accessToken", token);
        editor.apply();
    }

    public static void setSharedPrefRefresh(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_REFRESH, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("refreshToken", token);
        editor.apply();
    }

    public static String getSharedPrefAccess(Context c) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_PREF_ACCESS, Context.MODE_PRIVATE);
        return prefs.getString("accessToken", "");
    }

    public static String getSharedPrefRefresh(Context c) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_PREF_REFRESH, Context.MODE_PRIVATE);
        return prefs.getString("refreshToken", "");
    }
}
