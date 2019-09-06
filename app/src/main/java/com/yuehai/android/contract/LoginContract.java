package com.yuehai.android.contract;

import library.base.IBasePresenter;
import library.base.IBaseView;

/**
 * Login
 */
public interface LoginContract {
    interface View extends IBaseView {
        /**
         * 设置上次记住的登录名
         *
         * @param username 登录名
         */
        void initUserName(String username);

        /**
         * 登录成功
         */
        void onLoginSuccess();
    }

    interface Presenter extends IBasePresenter {

        /**
         * 登录
         *
         * @param userName 用户名
         * @param password 密码
         */
        void login(String userName, String password);
    }
}
