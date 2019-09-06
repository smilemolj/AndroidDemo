package com.yuehai.android.contract

import library.base.IBasePresenter
import library.base.IBaseView

/**
 * Login
 */
interface LoginContract {
    interface View : IBaseView {
        /**
         * 设置上次记住的登录名
         *
         * @param username 登录名
         */
        fun initUserName(username: String)

        /**
         * 登录成功
         */
        fun onLoginSuccess()
    }

    interface Presenter : IBasePresenter {

        /**
         * 登录
         *
         * @param userName 用户名
         * @param password 密码
         */
        fun login(userName: String, password: String)
    }
}
