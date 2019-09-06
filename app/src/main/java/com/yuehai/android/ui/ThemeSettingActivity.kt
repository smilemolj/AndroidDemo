package com.yuehai.android.ui

import android.os.Environment
import com.yuehai.android.R
import com.yuehai.android.contract.ThemeSettingContract
import com.yuehai.android.presenter.ThemeSettingPresenter
import com.zhy.changeskin.SkinManager
import kotlinx.android.synthetic.main.activity_theme_setting.*
import library.base.BaseMvpActivity
import java.io.File

/**
 * ThemeSetting V
 */
class ThemeSettingActivity : BaseMvpActivity<ThemeSettingContract.Presenter>(),
    ThemeSettingContract.View {

    override val innerViewId: Int
        get() = R.layout.activity_theme_setting

    override val toolbarTitle: Int
        get() = R.string.theme_setting

    private val mSkinPkgPath =
        Environment.getExternalStorageDirectory().toString() + File.separator + "skin_plugin.apk"

    override fun createPresenter(): ThemeSettingContract.Presenter {
        return ThemeSettingPresenter(this)
    }

    override fun init() {
        super.init()
        theme_switch.setOnCheckedChangeListener { _, _ ->
            SkinManager.getInstance().changeSkin(
                mSkinPkgPath,
                "com.imooc.skin_plugin",
                object : com.zhy.changeskin.callback.ISkinChangingCallback {
                    override fun onComplete() {
                    }

                    override fun onError(e: Exception?) {
                    }

                    override fun onStart() {
                    }
                })
        }
    }
}
