package com.yuehai.android.net

import com.yuehai.android.BuildConfig
import com.yuehai.android.net.interceptor.HttpLogger
import com.yuehai.android.net.interceptor.TokenInterceptor
import library.net.NetConfig
import library.net.ResponseVerify
import library.net.exception.ApiException
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject

class MyNetConfig : NetConfig, ResponseVerify {
    private val baseUrl = BuildConfig.BASE_URL
    private val interceptors: Array<Interceptor> = arrayOf(TokenInterceptor())
    private val networkInterceptors: Array<Interceptor> = arrayOf(
        HttpLoggingInterceptor(HttpLogger())
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    )

    override fun baseUrl(): String {
        return baseUrl
    }

    override fun interceptors(): Array<Interceptor> {
        return interceptors
    }

    override fun networkInterceptors(): Array<Interceptor> {
        return networkInterceptors
    }

    override fun responseVerify(): ResponseVerify? {
        return this
    }

    @Throws(JSONException::class)
    override fun verify(response: String) {
        val jsonObject = JSONObject(response)
        if (jsonObject.has("code") && !jsonObject.isNull("code")) {
            val code = jsonObject.getString("code")
            if (code != "10000") {
                val message = jsonObject.getString("message")
                throw ApiException(code.toInt(), message)
            }
        }
    }
}