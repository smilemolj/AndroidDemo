package com.yuehai.android.net.interceptor

import com.yuehai.android.util.LogUtil
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by zhaoyuehai 2019/5/5
 */
class HttpLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        LogUtil.d("HttpLogger", message)
    }
}