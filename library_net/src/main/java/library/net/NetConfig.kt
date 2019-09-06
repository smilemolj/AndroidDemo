package library.net

import okhttp3.Interceptor

/**
 * Created by zhaoyuehai 2019/9/6
 */
interface NetConfig {

    fun baseUrl(): String

    fun responseVerify(): ResponseVerify? {
        return null
    }

    fun interceptors(): Array<Interceptor>
    fun networkInterceptors(): Array<Interceptor>
}