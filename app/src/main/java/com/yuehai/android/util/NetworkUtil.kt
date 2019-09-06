package com.yuehai.android.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

import library.base.BaseApplication

/**
 * Created by zhaoyuehai 2019/4/2
 */
object NetworkUtil {
    /**
     * 检查WIFI是否连接
     */
    val isWifiConnected: Boolean
        get() {
            val connectivityManager =
                BaseApplication.instance.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val wifiInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            return wifiInfo != null
        }

    /**
     * 检查手机网络(4G/3G/2G)是否连接
     */
    val isMobileNetworkConnected: Boolean
        get() {
            val connectivityManager =
                BaseApplication.instance.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mobileNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            return mobileNetworkInfo != null
        }

    /**
     * 检查是否有可用网络
     */
    val isNetworkConnected: Boolean
        get() {
            val connectivityManager =
                BaseApplication.instance.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo != null
        }

    /**
     * 服务器是否开启
     * 属于网络请求，不能再UI线程执行此方法
     */
    @Throws(IOException::class)
    fun isConnServer(serverUrl: String): Boolean {
        var connFlag = false
        val url: URL
        var conn: HttpURLConnection? = null
        try {
            url = URL(serverUrl)
            conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = 3 * 1000
            if (conn.responseCode == 200) {// 如果连接成功则设置为true
                connFlag = true
            }
        } finally {
            conn?.disconnect()
        }
        return connFlag
    }
}
