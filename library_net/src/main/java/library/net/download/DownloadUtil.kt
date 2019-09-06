package library.net.download

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import library.net.NetHelper
import library.net.exception.ApiException
import library.net.util.FileUtil
import library.net.util.RxUtil
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.File

/**
 * 文件下载工具类
 * Created by zhaoyuehai 2019/9/5
 */
object DownloadUtil {
    /**
     * 下载文件
     *  注意申请读写权限
     * 【直接使用[NetHelper]提供的[OkHttpClient]下载】
     * 后续提供端点续传功能等
     *
     * @param listener 注意此回调发生在子线程
     * @param observer 建议使用[ResultObserver]
     * @param tag 取消下载时需要设置此tag 调用 fun cancelDownload(tag: Any)时传入
     */
    fun download(
        url: String,
        pathNames: Array<String>,
        fileName: String,
        listener: DownloadListener,
        observer: Observer<File>,
        tag: Any? = null
    ) {
        download(url, null, pathNames, fileName, listener, observer, tag)
    }

    /**
     * 下载文件
     *  注意申请读写权限
     * 【直接使用[NetHelper]提供的[OkHttpClient]下载】
     * 后续提供端点续传功能等
     *
     * @param listener 注意此回调发生在子线程
     * @param observer 建议使用[ResultObserver]
     * @param tag 取消下载时需要设置此tag 调用 fun cancelDownload(tag: Any)时传入
     */
    fun download(
        url: String,
        headers: Headers? = null,
        pathNames: Array<String>,
        fileName: String,
        listener: DownloadListener,
        observer: Observer<File>,
        tag: Any? = null
    ) {
        Observable.create(ObservableOnSubscribe<ResponseBody> { emitter ->
            val builder = Request.Builder()
            if (headers != null) builder.headers(headers)
            if (tag != null) builder.tag(tag.javaClass, tag)
            val request = builder
                .tag(DownloadListener::class.java, listener)
                .url(url)
                .get()

                .build()
            val responseBody = NetHelper.instance//okHttp同步下载
                .oKHttpClient()
                .newCall(request)
                .execute()
                .body()
            if (responseBody != null) {
                emitter.onNext(responseBody)
                emitter.onComplete()
            } else {
                emitter.onError(ApiException(ApiException.DOWNLOAD_EXCEPTION, "下载失败"))
            }
        })
            .map { responseBody ->
                val file = FileUtil.writeFile(
                    responseBody.byteStream(),
                    FileUtil.getLocalFilePath(pathNames),
                    fileName
                ) ?: throw ApiException(ApiException.DOWNLOAD_EXCEPTION, "文件写入失败")
                file
            }
            .compose(RxUtil.io_main())
            .subscribe(observer)
    }

    /**
     * 取消下载
     */
    fun cancelDownload(tag: Any) {
        val dispatcher = NetHelper.instance
            .oKHttpClient()
            .dispatcher()
        if (dispatcher.runningCallsCount() > 0) {
            for (call in dispatcher.runningCalls()) {
                if (tag == call.request().tag(tag.javaClass)) {
                    call.cancel()
                }
            }
        }
        if (dispatcher.queuedCallsCount() > 0) {
            for (call in dispatcher.queuedCalls()) {
                if (tag == call.request().tag(tag.javaClass)) {
                    call.cancel()
                }
            }
        }
    }
}