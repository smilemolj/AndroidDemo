package library.net

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import library.net.converter.MyGsonConverterFactory
import library.net.download.DownloadInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Created by zhaoyuehai 2019/4/19
 */
class NetHelper private constructor() {

    companion object {
        private const val READ_TIME_OUT = 15000L// 读取超过时间 单位:毫秒
        private const val WRITE_TIME_OUT = 15000L//写超过时间 单位:毫秒
        //private const val FILE_CACHE_SIZE = 1024 * 1024 * 100L//缓存大小100Mb

        private lateinit var config: NetConfig

        fun init(config: NetConfig) {
            this.config = config
        }

        val instance: NetHelper
            get() = ClassHolder.INSTANCE

        private val BASE_URL get() = config.baseUrl()
    }

    private object ClassHolder {
        val INSTANCE = NetHelper()
    }

    init {
        initDownloadInterceptor()

//        initCachePathAndSize()
        initOkHttpClient()

        initGson()
        initRetrofit()
    }

    private lateinit var mRetrofit: Retrofit
    private lateinit var mDownloadInterceptor: DownloadInterceptor
    private lateinit var mOkHttpClient: OkHttpClient
    private lateinit var mGson: Gson
    //    private lateinit var mCache: Cache
    private val serviceCache: MutableMap<String, Any?> = mutableMapOf()

    fun oKHttpClient(): OkHttpClient {
        return mOkHttpClient
    }

    fun gson(): Gson {
        return mGson
    }

    /**
     * 获取Api接口（默认缓存接口）
     *
     * @param tClass 接口类Class
     * @return Api接口
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> create(tClass: Class<T>): T {
        val service: T
        if (serviceCache.containsKey(tClass.name)) {
            service = serviceCache[tClass.name] as T
        } else {
            service = mRetrofit.create(tClass)
            serviceCache[tClass.name] = service
        }
        return service
    }

    private fun initDownloadInterceptor() {
        mDownloadInterceptor = DownloadInterceptor()
    }

//    /**
//     * 配置缓存大小与缓存地址
//     */
//    private fun initCachePathAndSize() {
//        val cacheFile = File(BaseApplication.INSTANCE.cacheDir, "cache")
//        mCache = Cache(cacheFile, FILE_CACHE_SIZE)
//    }


    /**
     * 配置okHttp
     */
    private fun initOkHttpClient() {
        val builder = OkHttpClient.Builder()
            .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.MILLISECONDS)
//            .cache(mCache)
        for (interceptor in config.interceptors()) {
            builder.addInterceptor(interceptor)
        }
        for (interceptor in config.networkInterceptors()) {
            builder.addNetworkInterceptor(interceptor)
        }
        builder.addNetworkInterceptor(mDownloadInterceptor)
        //绕过SSL验证
        val x509TrustManager = object : X509TrustManager {

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(x509Certificates: Array<X509Certificate>, s: String) {

            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(x509Certificates: Array<X509Certificate>, s: String) {

            }

            override fun getAcceptedIssuers(): Array<X509Certificate?> {
                return arrayOfNulls(0)
            }
        }
        var ssfFactory: SSLSocketFactory? = null
        try {
            val sc = SSLContext.getInstance("TLS")
            sc.init(null, arrayOf<TrustManager>(x509TrustManager), SecureRandom())
            ssfFactory = sc.socketFactory
        } catch (ignored: Exception) {
        }
        if (ssfFactory != null) {
            builder.sslSocketFactory(ssfFactory, x509TrustManager)
                .hostnameVerifier { _, _ -> true }
        }
        mOkHttpClient = builder.build()
    }

    private fun initGson() {
        mGson = GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create()
    }

    /**
     * 配置retrofit
     */
    private fun initRetrofit() {
        mRetrofit = Retrofit.Builder()
            .client(mOkHttpClient)
            .addConverterFactory(MyGsonConverterFactory(mGson, config.responseVerify()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    /**
     * 取消请求
     */
    fun cancelAll() {
        mOkHttpClient.dispatcher().cancelAll()
    }
}
