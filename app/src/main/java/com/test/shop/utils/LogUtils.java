package com.test.shop.utils;

import android.util.Log;

/**
 * Logging helper class.
 */
public class LogUtils {
    public static final String TAG = "ShopTestApp";

    public static boolean DEBUG = true;

    public static void setLoggable(boolean tag) {
        DEBUG = tag;
    }

    public static void i(String tag, String msg) {
        if (DEBUG)
            Log.i(tag, msg);

    }

    public static void i(String tag, String msg, Throwable tr) {
        if (DEBUG)
            Log.i(tag, msg, tr);
    }


    public static void v(String tag, String msg) {
        if (DEBUG)
            Log.v(tag, msg);

    }

    public static void v(String tag, String msg, Throwable tr) {
        if (DEBUG)
            Log.v(tag, msg, tr);
    }

    public static void e(String tag, String msg) {
        if (DEBUG)
            Log.e(tag, msg);

    }

    public static void d(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);

    }

    public static void e(String tag, String msg, Throwable tr) {
        if (DEBUG)
            Log.e(tag, msg, tr);
    }

}
