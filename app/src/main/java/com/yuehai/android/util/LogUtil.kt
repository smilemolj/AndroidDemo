package com.yuehai.android.util


import android.util.Log

import com.yuehai.android.BuildConfig
import com.yuehai.android.Contacts


/**
 * LogUtil
 */
object LogUtil {

    private val isDebug = BuildConfig.DEBUG
    private const val TAG = Contacts.LOG_TAG

    fun e(msg: String) {
        e(TAG, msg)
    }

    fun e(tag: String, msg: String) {
        if (isDebug) {
            Log.e(tag, msg)
        }
    }

    fun w(msg: String) {
        w(TAG, msg)
    }

    fun w(tag: String, msg: String) {
        if (isDebug) {
            Log.w(tag, msg)
        }
    }

    fun i(msg: String) {
        i(TAG, msg)
    }

    fun i(tag: String, msg: String) {
        if (isDebug) {
            Log.i(tag, msg)
        }
    }

    fun d(msg: String) {
        d(TAG, msg)
    }

    fun d(tag: String, msg: String) {
        if (isDebug) {
            Log.d(tag, msg)
        }
    }
}
