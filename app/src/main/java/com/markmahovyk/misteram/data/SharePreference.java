package com.markmahovyk.misteram.data;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

public class SharePreference {
    private static final String APP_TOKEN = "app_token";
    private static final String NOTIFICATION_TOKEN = "notification_token";
    private static final String APP_AUTH_TOKEN = "app_auth_token";
    private static final String USERNAME = "username";

    public static String getTokenApp(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(APP_TOKEN, null);
    }

    public static void setTokenApp(Context context, String appToken) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(APP_TOKEN, appToken)
                .apply();
    }

    public static String getTokenNotification(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(NOTIFICATION_TOKEN, null);
    }

    public static void setTokenNotification(Context context, String nToken) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(NOTIFICATION_TOKEN, nToken)
                .apply();

    }
    public static String getAppAuthToken(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(APP_AUTH_TOKEN, null);
    }

    public static void setTokenAppAuthToken(Context context, String appAuthToken) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(APP_AUTH_TOKEN, appAuthToken)
                .apply();

    }

    public static String getUsername(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(USERNAME, null);
    }

    public static void setUsername(Context context, String username) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(USERNAME, username)
                .apply();

    }
}
