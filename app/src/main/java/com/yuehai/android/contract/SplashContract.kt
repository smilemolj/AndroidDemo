package com.yuehai.android.contract

import library.base.IBasePresenter
import library.base.IBaseView

/**
 * 闪屏启动页
 * Created by zhaoyuehai 2019/3/22
 */
interface SplashContract {
    interface View : IBaseView {

        /**
         * 去主界面/登录页
         */
        fun goMain()
    }

    interface Presenter : IBasePresenter
}
