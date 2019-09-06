package library.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

/**
 * 所有Activity的基类
 *
 *
 * 完全自主布局时：
 * 使用getContentViewId()返回你的布局layout文件id。
 *
 * 在BaseActivity支持统一的Toolbar+无数据页面显示时：
 * 请使用getInnerViewId()返回你的内容布局（不需要toolbar）layout文件id；
 * 请使用getToolbarTitle()设置你的标题内容。
 *
 * 当你需要自己实现toolbar布局时：
 * 请使用getToolbarLayoutId()返回你的toolbar布局layout文件id；
 * 请使用getToolbarId()返回你的Toolbar的id；
 * 请使用getTitleId()返回你的TextView标题的id。
 */
abstract class BaseActivity : AppCompatActivity() {


    private var titleTV: TextView? = null
    private lateinit var mContentView: ViewGroup

    /**
     * 自主实现布局 setContentView
     * 注意：getContentViewId()和getInnerViewId()方法需要二选一去重写！
     *
     * @return 布局layout文件id
     */
    protected open val contentViewId: Int get() = -1

    /**
     * 使用支持toolbar和无数据页面展示
     * 注意：getContentViewId()和getInnerViewId()方法需要二选一去重写！
     *
     * @return 嵌套布局layout文件id
     */
    protected open val innerViewId: Int get() = -1

    /**
     * toolbar布局文件
     * 不用默认toolbar时，此方法返回自己的toolbar布局
     */
    protected open val toolbarLayoutId: Int @LayoutRes get() = R.layout.toolbar_layout

    /**
     * Toolbar的id
     * 不用默认toolbar时，此方法返回自己的toolbar布局中的toolbarId
     */
    protected open val toolbarId: Int @IdRes get() = R.id.toolbar

    /**
     * 标题TextView的id
     * 不用默认toolbar时，此方法返回自己的toolbar布局中的标题TextView的id
     */
    protected open val titleId: Int @IdRes get() = R.id.title_tv

    /**
     * toolbar标题
     *
     * @return 标题
     */
    protected open val toolbarTitle: Int get() = -1

    /**
     * 根布局设置背景
     *
     * @return The identifier of the resource.
     */
    protected open val backgroundResource: Int @DrawableRes get() = R.color.bgColor
    protected var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (contentViewId != -1) {//自主实现布局
            setContentView(contentViewId)
        } else if (innerViewId != -1) {//支持toolbar和无数据页面内嵌布局
            //根布局是个LinearLayout
            val rootView = LinearLayout(this)
            rootView.setBackgroundResource(backgroundResource)
            rootView.orientation = LinearLayout.VERTICAL
            setContentView(rootView)//此方法默认是宽高MATCH_PARENT
            //先添加toolbar
            View.inflate(this, toolbarLayoutId, rootView)
            //再添加FrameLayout作为主内容View
            mContentView = FrameLayout(this)
            View.inflate(this, innerViewId, mContentView)
            //mContentFL需要宽高MATCH_PARENT，不设置默认是WRAP_CONTENT
            rootView.addView(
                mContentView,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            //初始化toolbar并设置标题
            toolbar = findViewById<Toolbar>(toolbarId)
            setSupportActionBar(toolbar)
            supportActionBar?.title = ""
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            titleTV = findViewById(titleId)
            if (toolbarTitle != -1) {
                titleTV?.setText(toolbarTitle)
            } else {
                titleTV?.text = ""
            }
        }
    }

    protected open fun setToolbarTitle(title: String) {
        titleTV?.text = title
    }

    protected open fun onToolbarBack() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (innerViewId != -1) {
            when (item.itemId) {
                android.R.id.home -> onToolbarBack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected open fun exitAnimalEnable(): Boolean {
        return true
    }

    override fun finish() {
        super.finish()
        if (exitAnimalEnable())
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out)
    }
}