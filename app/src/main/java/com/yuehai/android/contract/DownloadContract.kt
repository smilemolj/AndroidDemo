package com.yuehai.android.contract

import com.yuehai.android.widget.TipDialogFragment
import library.base.IBasePresenter
import library.base.IBaseView

/**
 * 下载测试
 */
interface DownloadContract {
    interface View : IBaseView {
        fun showTipDialog(msg: String, listener: TipDialogFragment.OnClickListener)
        /**
         * 检查读写权限
         */
        fun checkWritePermission(): Boolean

        /**
         * 申请读写权限
         */
        fun requestWritePermission()

        /**
         * 注意此回调方法在子线程
         */
        fun setProgressValue(progress: Int)

        fun setMessage(msg: String)
    }

    interface Presenter : IBasePresenter {
        fun download(url: String)

        fun onBack()
    }
}
