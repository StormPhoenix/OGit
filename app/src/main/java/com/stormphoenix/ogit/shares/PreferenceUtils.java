package com.stormphoenix.ogit.shares;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by StormPhoenix on 17-2-26.
 * StormPhoenix is a intelligent Android developer.
 */

public class PreferenceUtils {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "pasword";
    public static final String TOKEN = "token";
    public static final String IS_LOGIN = "is_login";

    public static final String CLIENT_ID = "29d1a620d41d6f07587a";
    public static final String CLIENT_SECRET = "5ff43c7722a37e272fa20db77e1ca1de74c9ea38";

    public static boolean isLogin(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(IS_LOGIN, false);
    }

    public static String getString(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null);
    }

    public static String getToken(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(TOKEN, null);
    }

    public static String getUsername(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(USERNAME, null);
    }

    public static String getPassword(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PASSWORD, null);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(key, value).commit();
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(key, value).commit();
    }
}
