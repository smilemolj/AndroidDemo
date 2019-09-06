package com.yuehai.android.contract

import library.base.IBasePresenter
import library.base.IBaseView

/**
 * 注册/修改用户
 * Created by zhaoyuehai 2019/3/22
 */
interface RegisterContract {
    interface View : IBaseView {
        /**
         * 注册成功
         */
        fun onRegisterSuccess(username: String, userId: Long?)

        /**
         * 修改成功
         */
        fun onModifySuccess()
    }

    interface Presenter : IBasePresenter {
        /**
         * 注册
         *
         * @param userName 用户名
         * @param password 密码
         * @param phone    手机号
         */
        fun onRegister(userName: String, password: String, phone: String)

        /**
         * 修改用户
         */
        fun onModifyUser(id: Long, phone: String, email: String?, nickName: String?)
    }
}
