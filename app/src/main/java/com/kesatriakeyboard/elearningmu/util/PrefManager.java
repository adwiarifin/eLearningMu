package com.kesatriakeyboard.elearningmu.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PrefManager {

    private static PrefManager mInstance;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    // Shared preferences file name
    private static final String PREF_NAME = "com.kesatriakeyboard.elearningmu";

    private static final String LOGGED_IN = "logged_in";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String USER_ID = "user_id";

    public PrefManager(Context context) {
        Log.d("PrefManager", "PrefManager contructor is called");
        this.context = context;
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public static synchronized PrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PrefManager(context);
        }
        return mInstance;
    }

    public void setLoggedIn(boolean loggedIn) {
        editor = pref.edit();
        editor.putBoolean(LOGGED_IN, loggedIn);
        editor.apply();
    }

    public void setAccessToken(String accessToken) {
        editor = pref.edit();
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.apply();
    }

    public void setRefreshToken(String refreshToken) {
        editor = pref.edit();
        editor.putString(REFRESH_TOKEN, refreshToken);
        editor.apply();
    }

    public void setUserId(int userId) {
        editor = pref.edit();
        editor.putInt(USER_ID, userId);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(LOGGED_IN, false);
    }

    public String getAccessToken() {
        return pref.getString(ACCESS_TOKEN, "");
    }

    public String getRefreshToken() {
        return pref.getString(REFRESH_TOKEN, "");
    }

    public int getUserId() {
        return pref.getInt(USER_ID, 0);
    }
}
