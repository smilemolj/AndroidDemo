package com.yuehai.android.ui

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.yuehai.android.R
import com.yuehai.android.contract.RegisterContract
import com.yuehai.android.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_register.*
import library.base.BaseMvpActivity

/**
 * 注册 V
 * Created by zhaoyuehai 2019/3/22
 */
class RegisterActivity : BaseMvpActivity<RegisterContract.Presenter>(), RegisterContract.View,
    View.OnClickListener {

    override val innerViewId: Int
        get() = R.layout.activity_register

    override val toolbarTitle: Int
        get() = R.string.register

    private var isModify = false
    private var id = -1L


    override fun createPresenter(): RegisterContract.Presenter {
        return RegisterPresenter(this)
    }

    override fun init() {
        super.init()
        isModify = intent.getBooleanExtra("isModify", false)
        if (isModify) {
            setToolbarTitle("修改")
            confirm_btn.setText(R.string.commit)
            modify_ll.visibility = View.VISIBLE
            register_ll.visibility = View.GONE
            id = intent.getLongExtra("id", -1L)
            val userName = intent.getStringExtra("userName")
            val phone = intent.getStringExtra("phone")
            if (id != -1L && userName != null && phone != null) {
                tv_1.text = String.format("用户名：%s", userName)
                et_5.setText(phone)
                val email = intent.getStringExtra("email")
                if (email != null) {
                    et_6.setText(email)
                }
                val nickName = intent.getStringExtra("nickName")
                if (nickName != null) {
                    et_4.setText(nickName)
                }
            }
        }
        confirm_btn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.confirm_btn//添加用户
            -> if (isModify) {
                if (id == -1L) return
                val phone = et_5.text.toString().trim()
                if (TextUtils.isEmpty(phone)) {
                    et_5.error = "请输入手机号"
                    et_5.requestFocus()
                    return
                }
                val email = et_6.text.toString().trim()
                val nickName = et_4.text.toString().trim()
                presenter.onModifyUser(id, phone, email, nickName)
            } else {
                val userName = et_1.text.toString().trim()
                if (TextUtils.isEmpty(userName)) {
                    et_1.error = "请输入用户名"
                    et_1.requestFocus()
                    return
                }
                val password = et_2.text.toString().trim()
                if (TextUtils.isEmpty(password)) {
                    et_2.error = "请输入密码"
                    et_2.requestFocus()
                    return
                }
                val phone = et_3.text.toString().trim()
                if (TextUtils.isEmpty(phone)) {
                    et_3.error = "请输入手机号"
                    et_3.requestFocus()
                    return
                }
                presenter.onRegister(userName, password, phone)
            }
        }
    }

    override fun onRegisterSuccess(username: String, userId: Long?) {
        val intent = Intent()
        intent.putExtra("username", username)
        intent.putExtra("userId", userId)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onModifySuccess() {
        val intent = Intent()
        intent.putExtra("userId", id)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}