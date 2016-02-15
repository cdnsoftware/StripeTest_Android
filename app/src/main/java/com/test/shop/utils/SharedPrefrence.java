package com.test.shop.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by devendrasahu on 3/11/15.
 */

/**
 * @author akshay soni
 */
public class SharedPrefrence {

    public static final String EMAIL = "EMAIL";
    public static final String ID = "ID";
    public static final String IS_SOCIAL = "social";
    public static final String IS_LOGIN = "is_login";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String TOKEN_ID = "TOKEN_ID";
    private static final String GS_PREFS_NAME = "GS_SHARED_PREFS";
    public static String isFirstLaunch = "isFirstLaunch";
    private static SharedPreferences _sPrefs = null;
    private static SharedPreferences.Editor _editor = null;
    private static SharedPrefrence _instance = null;


    public SharedPrefrence() {

    }

    private SharedPrefrence(Context context) {
        _sPrefs = context.getSharedPreferences(GS_PREFS_NAME,
                Context.MODE_PRIVATE);
    }

    public static SharedPrefrence getInstance(Context context) {
        if (_instance == null) {
            _instance = new SharedPrefrence(context);
        }
        return _instance;
    }

    public String readPrefs(String pref_name) {
        return _sPrefs.getString(pref_name, "");
    }

    public void writePrefs(String pref_name, String pref_val) {
        _editor = _sPrefs.edit();
        _editor.putString(pref_name, pref_val);
        _editor.apply();
    }

    public void clearPrefs() {
        _editor = _sPrefs.edit();
        _editor.clear();
        _editor.apply();
    }

    public boolean readBooleanPrefs(String pref_name) {
        return _sPrefs.getBoolean(pref_name, false);
    }

    public void writeBooleanPrefs(String pref_name, boolean pref_val) {
        _editor = _sPrefs.edit();
        _editor.putBoolean(pref_name, pref_val);
        _editor.apply();
    }

    public int readIntPrefs(String pref_name) {
        return _sPrefs.getInt(pref_name, 0);
    }

    public void writeIntPrefs(String pref_name, int pref_val) {
        _editor = _sPrefs.edit();
        _editor.putInt(pref_name, pref_val);
        _editor.apply();
    }

    public float readFloatPrefs(String pref_name) {
        return _sPrefs.getFloat(pref_name, 0);
    }

    public void writeFlatPrefs(String pref_name, float pref_val) {
        _editor = _sPrefs.edit();
        _editor.putFloat(pref_name, pref_val);
        _editor.apply();
    }

    public String readTermPrefs(String pref_name) {
        return _sPrefs.getString(pref_name, "");
    }

    public void writeTermPrefs(String pref_name, String pref_val) {
        _editor = _sPrefs.edit();
        _editor.putString(pref_name, pref_val);
        _editor.apply();
    }


}