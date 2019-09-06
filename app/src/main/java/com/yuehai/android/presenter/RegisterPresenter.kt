package com.yuehai.android.presenter

import android.util.Base64

import com.yuehai.android.contract.RegisterContract
import com.yuehai.android.net.ApiUtil
import com.yuehai.android.net.request.RegisterUserBean
import com.yuehai.android.net.response.ResultBean
import com.yuehai.android.net.response.UserForListBean

import io.reactivex.disposables.Disposable
import library.base.BasePresenter
import library.net.ResultObserver
import library.net.util.RxUtil

/**
 * 注册/修改 P
 * Created by zhaoyuehai 2019/3/22
 */
class RegisterPresenter(view: RegisterContract.View) : BasePresenter<RegisterContract.View>(view),
    RegisterContract.Presenter {

    override fun onRegister(
        userName: String,
        password: String,
        phone: String
    ) {//password->Base64进行编码
        ApiUtil.instance
            .apiService
            .register(
                RegisterUserBean(
                    userName,
                    Base64.encodeToString(password.toByteArray(), Base64.NO_WRAP),
                    phone
                )
            )
            .compose(RxUtil.io_main())
            .subscribe(object : ResultObserver<ResultBean<Long>>(this) {

                override fun onSubscribe(d: Disposable) {
                    view?.showLoading()
                }

                override fun onNext(result: ResultBean<Long>) {
                    view?.showToast(result.message)
                    view?.dismissLoading()
                    view?.onRegisterSuccess(userName, result.data)
                }

                override fun onError(message: String) {
                    view?.dismissLoading()
                    view?.showToast(message)
                }
            })
    }

    override fun onModifyUser(id: Long, phone: String, email: String?, nickName: String?) {
        ApiUtil.instance
            .apiService
            .updateUser(UserForListBean(id, phone, email, nickName))
            .compose(RxUtil.io_main())
            .subscribe(object : ResultObserver<ResultBean<Any>>(this) {

                override fun onSubscribe(d: Disposable) {
                    view?.showLoading()
                }

                override fun onNext(result: ResultBean<Any>) {
                    view?.showToast(result.message)
                    view?.dismissLoading()
                    view?.onModifySuccess()
                }

                override fun onError(message: String) {
                    view?.dismissLoading()
                    view?.showToast(message)
                }
            })
    }
}
