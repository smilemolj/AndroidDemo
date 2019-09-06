package com.yuehai.android.ui

import com.yuehai.android.R
import com.yuehai.android.contract.SetPwdContract
import com.yuehai.android.presenter.SetPwdPresenter

import library.base.BaseMvpActivity

/**
 * SetPwd V
 */
class SetPwdActivity : BaseMvpActivity<SetPwdContract.Presenter>(), SetPwdContract.View {

    override val toolbarTitle: Int
        get() = R.string.reset_pwd

    override val innerViewId: Int
        get() = R.layout.activity_set_password

    override fun createPresenter(): SetPwdContract.Presenter {
        return SetPwdPresenter(this)
    }

}
