package library.net.download

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 下载进度监听拦截器
 * Created by zhaoyuehai 2019/9/5
 */
class DownloadInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val listener = chain.request().tag(DownloadListener::class.java)
        return if (listener != null) {
            val request = chain.request()
                .newBuilder()
                .tag(DownloadListener::class.java, null)//删除 tag
                .method(chain.request().method(), chain.request().body())
                .build()
            val response = chain.proceed(request)
            response.newBuilder()
                .body(
                    ProgressResponseBody(
                        response.body(),
                        listener
                    )
                )
                .build()
        } else {
            chain.proceed(chain.request())
        }
    }
}