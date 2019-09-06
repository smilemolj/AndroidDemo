package com.yuehai.android.presenter

import com.yuehai.android.contract.DownloadContract
import com.yuehai.android.net.interceptor.TokenInterceptor
import com.yuehai.android.util.FileUtil
import io.reactivex.disposables.Disposable
import library.base.BasePresenter
import library.net.ResultObserver
import library.net.download.DownloadListener
import library.net.download.DownloadUtil
import okhttp3.Headers
import java.io.File

/**
 * 下载测试 P
 */
class DownloadPresenter(view: DownloadContract.View) : BasePresenter<DownloadContract.View>(view),
    DownloadContract.Presenter, DownloadListener {

    override fun download(url: String) {
        val fileName = FileUtil.getFileNameByUrl(url)
        if (fileName == null) {
            view?.showToast("下载地址格式错误！")
            return
        }
        DownloadUtil.download(url,
            Headers.Builder()
                .add(TokenInterceptor.HEADER_NO_TOKEN)
                .build(),
            arrayOf("yuehai", "download"), fileName, this,
            object : ResultObserver<File?>(this) {

                override fun onSubscribe(d: Disposable) {
                    super.onSubscribe(d)
                    view?.setMessage("文件马上开始下载...")
                }

                override fun onError(message: String) {
                    view?.dismissLoading()
                    view?.setMessage("下载失败：${message}")
                }

                override fun onNext(f: File) {
                    view?.showToast("下载成功！")
                    view?.setMessage("下载成功！\n文件大小：${f.length() / 1024 / 1024}M\n文件路径：${f.absolutePath}")
                }
            })
    }

    override fun onProgress(currentLength: Long, totalLength: Long) {
        view?.setProgressValue((currentLength * 100 / totalLength).toInt())
    }
}
