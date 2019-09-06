package com.yuehai.android.contract;

import androidx.annotation.Nullable;

import library.base.IBasePresenter;
import library.base.IBaseView;

/**
 * 注册/修改用户
 * Created by zhaoyuehai 2019/3/22
 */
public interface RegisterContract {
    interface View extends IBaseView {
        /**
         * 注册成功
         */
        void onRegisterSuccess(String username, Long userId);

        /**
         * 修改成功
         */
        void onModifySuccess();
    }

    interface Presenter extends IBasePresenter {
        /**
         * 注册
         *
         * @param userName 用户名
         * @param password 密码
         * @param phone    手机号
         */
        void onRegister(String userName, String password, String phone);

        /**
         * 修改用户
         */
        void onModifyUser(long id, String phone, @Nullable String email, @Nullable String nickName);
    }
}
