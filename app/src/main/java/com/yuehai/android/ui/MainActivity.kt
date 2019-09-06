package com.yuehai.android.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.yuehai.android.R
import com.yuehai.android.UserData
import kotlinx.android.synthetic.main.activity_main.*
import library.base.BaseActivity

/**
 * 主页
 * Created by zhaoyuehai 2019/3/22
 */
class MainActivity : BaseActivity(), View.OnClickListener {
    override val innerViewId: Int
        get() = R.layout.activity_main

    override val toolbarTitle: Int
        get() = R.string.main

    override fun exitAnimalEnable(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        test_btn3.setText(R.string.download_demo)
        test_btn4.setText(R.string.theme_setting)
        test_btn0.setOnClickListener(this)
        test_btn1.setOnClickListener(this)
        test_btn2.setOnClickListener(this)
        test_btn3.setOnClickListener(this)
        test_btn4.setOnClickListener(this)
        checkUser()
    }

    private fun checkUser() {
        val user = UserData.instance.getUser()
        if (user != null) {
            test_btn0.visibility = View.GONE
            test_btn1.visibility = View.VISIBLE
            test_tv.visibility = View.VISIBLE
            test_tv.text = String.format("当前用户：%s", user.userName)
        } else {
            test_btn0.visibility = View.VISIBLE
            test_btn1.visibility = View.GONE
            test_tv.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.test_btn0 -> startActivityForResult(Intent(this, LoginActivity::class.java), 1000)
            R.id.test_btn1 -> {
                UserData.instance.clearUser()
                checkUser()
            }
            R.id.test_btn2 -> startActivity(Intent(this@MainActivity, UserListActivity::class.java))
            R.id.test_btn3 -> startActivity(Intent(this@MainActivity, DownloadActivity::class.java))
            R.id.test_btn4 -> startActivity(
                Intent(
                    this@MainActivity,
                    ThemeSettingActivity::class.java
                )
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {//登录成功
            checkUser()
        }
    }
}
