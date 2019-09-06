package com.yuehai.android.presenter

import com.yuehai.android.contract.SplashContract
import io.reactivex.Observable
import library.base.BasePresenter
import library.net.util.RxUtil
import java.util.concurrent.TimeUnit

/**
 * 闪屏启动页 P
 * Created by zhaoyuehai 2019/3/22
 */
class SplashPresenter(view: SplashContract.View) : BasePresenter<SplashContract.View>(view),
    SplashContract.Presenter {


    override fun onCreate() {
        super.onCreate()
        add(Observable.timer(2, TimeUnit.SECONDS)
            .compose(RxUtil.io_main())
            .subscribe {
                view?.goMain()
            }
        )
    }
}
