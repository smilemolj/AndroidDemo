package com.yuehai.android.ui

import android.content.pm.PackageManager
import android.os.Build
import com.yuehai.android.R
import com.yuehai.android.contract.DownloadContract
import com.yuehai.android.presenter.DownloadPresenter
import com.yuehai.android.widget.TipDialogFragment
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
            presenter.download(url_et.text.toString())
        }
    }

    override fun checkWritePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    override fun requestWritePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1000
            )
    }

    override fun showTipDialog(msg: String, listener: TipDialogFragment.OnClickListener) {
        TipDialogFragment(msg, listener).show(supportFragmentManager, "tipDialog")
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


    override fun onBackPressed() {
        presenter.onBack()
        super.onBackPressed()
    }

    override fun onToolbarBack() {
        presenter.onBack()
        super.onToolbarBack()
    }
}
