package com.yuehai.android.net

import library.net.NetHelper

/**
 * Created by zhaoyuehai 2019/3/29
 */
class ApiUtil private constructor() {

    companion object {
        val instance: ApiUtil
            get() = ClassHolder.INSTANCE
    }

    private object ClassHolder {
        val INSTANCE = ApiUtil()
    }

    val apiService: ApiService = NetHelper.instance.create(ApiService::class.java)

    /**
     * 取消请求
     */
    fun cancelAll() {
        NetHelper.instance.cancelAll()
    }
}
