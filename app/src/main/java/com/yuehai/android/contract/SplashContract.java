package com.yuehai.android.contract;

import library.base.IBasePresenter;
import library.base.IBaseView;

/**
 * 闪屏启动页
 * Created by zhaoyuehai 2019/3/22
 */
public interface SplashContract {
    interface View extends IBaseView {

        /**
         * 去主界面/登录页
         */
        void goMain();
    }

    interface Presenter extends IBasePresenter {
    }
}
