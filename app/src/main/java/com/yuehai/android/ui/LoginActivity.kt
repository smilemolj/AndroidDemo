package com.yuehai.android.ui

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.yuehai.android.R
import com.yuehai.android.contract.LoginContract
import com.yuehai.android.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import library.base.BaseMvpActivity

/**
 * Login V
 */
class LoginActivity : BaseMvpActivity<LoginContract.Presenter>(), LoginContract.View,
    View.OnClickListener {

    override val toolbarTitle: Int
        get() = R.string.login

    override val innerViewId: Int
        get() = R.layout.activity_login

    override fun createPresenter(): LoginContract.Presenter {
        return LoginPresenter(this)
    }

    override fun init() {
        super.init()
        login_btn.setOnClickListener(this)
        register_go_tv.setOnClickListener(this)
        find_pwd_tv.setOnClickListener(this)
    }

    override fun initUserName(username: String) {
        name_et.setText(username)
        pwd_et.requestFocus()
    }

    override fun onLoginSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.find_pwd_tv -> startActivity(Intent(this, SetPwdActivity::class.java))
            R.id.register_go_tv -> startActivityForResult(
                Intent(
                    this,
                    RegisterActivity::class.java
                ), 2000
            )
            R.id.login_btn -> {
                val userName = name_et.text.toString().trim()
                if (TextUtils.isEmpty(userName)) {
                    name_et.error = "请输入用户名"
                    name_et.requestFocus()
                    return
                }
                val password = pwd_et.text.toString().trim()
                if (TextUtils.isEmpty(password)) {
                    pwd_et.error = "请输入密码"
                    pwd_et.requestFocus()
                    return
                }
                presenter.login(userName, password)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2000 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val username = data.getStringExtra("username")
                if (username != null) {
                    name_et.setText(username)
                    pwd_et.requestFocus()
                }
            }
        }
    }
}
