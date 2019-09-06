package com.yuehai.android.contract

import library.base.IBasePresenter
import library.base.IBaseView

/**
 * 下载测试
 */
interface DownloadContract {
    interface View : IBaseView {
        /**
         * 注意此回调方法在子线程
         */
        fun setProgressValue(progress: Int)

        fun setMessage(msg: String)
    }

    interface Presenter : IBasePresenter {
        fun download(url: String)
    }
}
