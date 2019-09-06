package com.yuehai.android.ui

import android.content.Intent

import com.yuehai.android.Contacts
import com.yuehai.android.R
import com.yuehai.android.contract.SplashContract
import com.yuehai.android.presenter.SplashPresenter
import com.yuehai.android.util.SPUtil
import com.yuehai.android.widget.InputDialogFragment

import library.base.BaseMvpActivity

/**
 * 闪屏启动页 V
 * Created by zhaoyuehai 2019/3/22
 */
class SplashActivity : BaseMvpActivity<SplashContract.Presenter>(), SplashContract.View {

    override val contentViewId: Int
        get() = R.layout.activity_splash

    override fun createPresenter(): SplashContract.Presenter {
        return SplashPresenter(this)
    }

    override fun exitAnimalEnable(): Boolean {
        return false
    }

    override fun goMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
