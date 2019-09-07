package com.yuehai.android.presenter

import com.yuehai.android.Contacts
import com.yuehai.android.contract.DownloadContract
import com.yuehai.android.net.interceptor.TokenInterceptor
import com.yuehai.android.util.FileUtil
import com.yuehai.android.widget.TipDialogFragment
import io.reactivex.disposables.Disposable
import library.base.BasePresenter
import library.net.ResultObserver
import library.net.download.DownloadListener
import library.net.download.DownloadUtil
import okhttp3.Call
import okhttp3.Headers
import java.io.File

/**
 * 下载测试 P
 */
class DownloadPresenter(view: DownloadContract.View) : BasePresenter<DownloadContract.View>(view),
    DownloadContract.Presenter, DownloadListener {
    private var downloadCall: Call? = null
    override fun download(url: String) {
        if (view?.checkWritePermission() == false) {
            view?.showTipDialog("下载文件需要手机读写权限，请您允许！", object : TipDialogFragment.OnClickListener {
                override fun onCancel() {
                    view?.showToast("缺少手机读写权限！")
                }

                override fun onConfirm() {
                    view?.requestWritePermission()
                }
            })
            return
        }
        if (url.isEmpty()) {
            view?.showToast("请输入下载地址！")
            return
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            view?.showToast("下载地址格式错误！")
            return
        }
        val fileName = FileUtil.getFileNameByUrl(url)
        if (fileName == null) {
            view?.showToast("下载地址格式错误！")
            return
        }
        downloadCall = DownloadUtil.download(
            url,
            Headers.Builder()
                .add(TokenInterceptor.HEADER_NO_TOKEN)
                .build(),
            Contacts.DOWNLOAD_PATH_NAMES, fileName, this,
            object : ResultObserver<File>(this) {

                override fun onSubscribe(d: Disposable) {
                    super.onSubscribe(d)
                    view?.setMessage("文件马上开始下载...")
                }

                override fun onError(message: String) {
                    view?.dismissLoading()
                    view?.showToast(message)
                    view?.setMessage("下载失败：$message")
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

    override fun onBack() {
        if (downloadCall?.isCanceled == false) {
            downloadCall?.cancel()
        }
    }
}
