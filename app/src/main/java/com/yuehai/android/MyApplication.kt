package com.yuehai.android

import com.yuehai.android.net.MyNetConfig
import com.yuehai.android.util.LogUtil
import io.reactivex.plugins.RxJavaPlugins
import library.base.BaseApplication
import library.net.NetHelper

/**
 * Created by zhaoyuehai 2019/3/22
 */
class MyApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        NetHelper.init(MyNetConfig())
        //捕获RxJava在取消订阅后发生的异常
        RxJavaPlugins.setErrorHandler {
            LogUtil.e(it.message ?: "RxJavaError")
        }
    }
}