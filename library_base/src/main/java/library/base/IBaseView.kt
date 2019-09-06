package library.base

import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner

/**
 * Created by zhaoyuehai 2019/3/15
 */
interface IBaseView : LifecycleOwner {
    /**
     * 显示加载弹框
     */
    fun showLoading()

    /**
     * 显示带内容的加载弹框
     * @param msg 字数 <= 7
     */
    fun showLoading(msg: String)

    /**
     * 隐藏加载弹框
     */
    fun dismissLoading()

    /**
     * 弹提示
     *
     * @param msg 提示内容
     */
    fun showToast(msg: String)

    /**
     * 弹提示
     *
     * @param strId 提示内容
     */
    fun showToast(@StringRes strId: Int)

}
