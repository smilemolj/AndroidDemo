package com.yuehai.android.presenter

import android.text.TextUtils
import android.util.Base64

import com.yuehai.android.Contacts
import com.yuehai.android.UserData
import com.yuehai.android.contract.LoginContract
import com.yuehai.android.net.ApiUtil
import com.yuehai.android.net.response.ResultBean
import com.yuehai.android.net.response.UserBean
import com.yuehai.android.util.SPUtil

import io.reactivex.disposables.Disposable
import library.base.BasePresenter
import library.net.ResultObserver
import library.net.util.RxUtil

/**
 * Login P
 */
class LoginPresenter(view: LoginContract.View) : BasePresenter<LoginContract.View>(view),
    LoginContract.Presenter {

    override fun onCreate() {
        super.onCreate()
        val username = SPUtil.getInstance(Contacts.SP_NAME).getString(Contacts.LOGIN_NAME)
        if (!TextUtils.isEmpty(username)) {
            view?.initUserName(username)
        }
    }

    override fun login(userName: String, password: String) {
        SPUtil.getInstance(Contacts.SP_NAME).put(Contacts.LOGIN_NAME, userName)
        ApiUtil.instance
            .apiService
            .login(userName, Base64.encodeToString(password.toByteArray(), Base64.NO_WRAP))
            .compose(RxUtil.io_main())
            .subscribe(object : ResultObserver<ResultBean<UserBean>>(this) {
                override fun onError(message: String) {
                    view?.dismissLoading()
                    view?.showToast(message)
                }

                override fun onSubscribe(d: Disposable) {
                    super.onSubscribe(d)
                    view?.showLoading()
                }

                override fun onNext(bean: ResultBean<UserBean>) {
                    view?.dismissLoading()
                    view?.showToast(bean.message)
                    val data = bean.data
                    if (data != null) {
                        UserData.instance.saveUser(data)
                        view?.onLoginSuccess()
                    }
                }
            })
    }
}
