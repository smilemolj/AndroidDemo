package com.yuehai.android

import com.yuehai.android.net.interceptor.HttpLogger
import com.yuehai.android.net.interceptor.TokenInterceptor
import library.base.BaseApplication
import library.net.NetConfig
import library.net.NetHelper
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by zhaoyuehai 2019/3/22
 */
class MyApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        NetHelper.init(getNetConfig())
    }

    private fun getNetConfig(): NetConfig {
        return NetConfig(BuildConfig.BASE_URL)
            .addInterceptor(TokenInterceptor())
            .addNetworkInterceptor(
                HttpLoggingInterceptor(HttpLogger())
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
    }
}