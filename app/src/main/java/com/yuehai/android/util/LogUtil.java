package com.yuehai.android.util;


import android.util.Log;

import com.yuehai.android.BuildConfig;
import com.yuehai.android.Contacts;


/**
 * LogUtil
 */
public class LogUtil {

    private static boolean isDebug = BuildConfig.DEBUG;
    private static final String TAG = Contacts.LOG_TAG;

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, msg);
        }
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }
}
