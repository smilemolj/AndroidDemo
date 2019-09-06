package library.net

import okhttp3.Interceptor
import java.util.*

/**
 * Created by zhaoyuehai 2019/9/6
 */
class NetConfig(val baseUrl: String, val responseVerify: ResponseVerify? = null) {
    val interceptors: MutableList<Interceptor> = ArrayList()
    val networkInterceptors: MutableList<Interceptor> = ArrayList()

    fun addInterceptor(interceptor: Interceptor): NetConfig {
        interceptors.add(interceptor)
        return this
    }

    fun addNetworkInterceptor(interceptor: Interceptor): NetConfig {
        networkInterceptors.add(interceptor)
        return this
    }
}