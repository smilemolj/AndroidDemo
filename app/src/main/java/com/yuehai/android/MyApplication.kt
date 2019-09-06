package com.yuehai.android

import com.yuehai.android.net.MyNetConfig
import library.base.BaseApplication
import library.net.NetHelper

/**
 * Created by zhaoyuehai 2019/3/22
 */
class MyApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        NetHelper.init(MyNetConfig())
    }
}