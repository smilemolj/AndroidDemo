package library.net.upload

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import library.net.NetHelper
import library.net.exception.ApiException
import library.net.util.RxUtil
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.File


/**
 * 文件上传工具类
 * 注意：申请读写权限
 * Created by zhaoyuehai 2019/9/5
 */
object UploadUtil {
    /**
     * 上传图片 POST
     *
     * 注意申请读写权限
     * 【直接使用[NetHelper]提供的[OkHttpClient]下载】
     *
     * @param imgPaths 图片途径集合
     * @param isZip 是否对图片压缩
     * @param observer 建议使用[ResultObserver]
     */
    fun uploadImages(
        url: String,
        headers: Headers? = null,
        imgPaths: List<String>,
        isZip: Boolean,
        requestBody: ProgressRequestBody,
        observer: Observer<ResponseBody>
    ) {
        Observable.create(ObservableOnSubscribe<ResponseBody> { emitter ->
            val imageFiles = ArrayList<File>()
            for (path in imgPaths) {
                val imgFile = File(path)
                if (imgFile.exists()) {
                    imageFiles.add(imgFile)
                }
            }
            val builder = Request.Builder()
            if (headers != null) builder.headers(headers)
            val request = builder
                .url(url)
                .post(requestBody)
                .build()
            val responseBody = NetHelper.instance//okHttp同步上传
                .oKHttpClient()
                .newCall(request)
                .execute()
                .body()
            if (responseBody != null) {
                emitter.onNext(responseBody)
                emitter.onComplete()
            } else {
                emitter.onError(ApiException(ApiException.UPLOAD_EXCEPTION, "上传失败"))
            }
        })
            .compose(RxUtil.io_main())
            .subscribe(observer)
    }
}
