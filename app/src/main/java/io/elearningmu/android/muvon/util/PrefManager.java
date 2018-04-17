package io.elearningmu.android.muvon.util;

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
    private static final String EXPIRES = "expires";
    private static final String USER_ID = "user_id";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_NAME = "user_name";
    private static final String USER_AVATAR = "user_avatar";

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

    public void setExpires(long expires) {
        editor = pref.edit();
        editor.putLong(EXPIRES, expires);
        editor.apply();
    }

    public void setUserId(int userId) {
        editor = pref.edit();
        editor.putInt(USER_ID, userId);
        editor.apply();
    }

    public void setUserEmail(String userEmail) {
        editor = pref.edit();
        editor.putString(USER_EMAIL, userEmail);
        editor.apply();
    }

    public void setUserName(String userName) {
        editor = pref.edit();
        editor.putString(USER_NAME, userName);
        editor.apply();
    }

    public void setUserAvatar(String userAvatar) {
        editor = pref.edit();
        editor.putString(USER_AVATAR, userAvatar);
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

    public long getExpires() {
        return pref.getLong(EXPIRES, 0);
    }

    public int getUserId() {
        return pref.getInt(USER_ID, 0);
    }

    public String getUserEmail() {
        return pref.getString(USER_EMAIL, "");
    }

    public String getUserName() {
        return pref.getString(USER_NAME, "");
    }

    public String getUserAvatar() {
        return pref.getString(USER_AVATAR, "");
    }
}
