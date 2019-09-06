package com.yuehai.android.ui

import com.yuehai.android.R
import com.yuehai.android.contract.DownloadContract
import com.yuehai.android.presenter.DownloadPresenter
import kotlinx.android.synthetic.main.activity_download.*
import library.base.BaseMvpActivity

/**
 * 下载测试 V
 */
class DownloadActivity : BaseMvpActivity<DownloadContract.Presenter>(), DownloadContract.View {
    override val toolbarTitle: Int
        get() = R.string.download_demo

    override val innerViewId: Int
        get() = R.layout.activity_download

    override fun createPresenter(): DownloadContract.Presenter {
        return DownloadPresenter(this)
    }

    //        private val defaultDownloadUrl =
//        "https://power.cnecloud.com/upgrade/app-power-release.apk"
    private val defaultDownloadUrl =
        "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk"

    override fun init() {
        super.init()
        url_et.setText(defaultDownloadUrl)
        btn.setOnClickListener {
            val url = url_et.text.toString()
            if (url.isEmpty()) {
                showToast("请输入下载地址！")
                return@setOnClickListener
            }
            if (url.startsWith("http://") || url.startsWith("https://")) {
                presenter.download(url)
            } else {
                showToast("下载地址格式错误！")
            }
        }
    }

    override fun setMessage(msg: String) {
        result_tv.text = msg
    }

    override fun setProgressValue(progress: Int) {
        runOnUiThread {
            Thread.currentThread()
            pb.progress = progress
            result_tv.text = "文件下载中------$progress%"
        }
    }
}
