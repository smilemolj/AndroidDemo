package library.base

import android.os.Bundle
import library.base.utils.MyProgressDialog
import library.base.utils.ProgressDialogUtil
import library.base.utils.ToastUtil

/**
 * MVP模式Activity的基类
 */
abstract class BaseMvpActivity<P : IBasePresenter> : BaseActivity(), IBaseView {

    protected lateinit var presenter: P

    private var loadingDialog: MyProgressDialog? = null

    protected abstract fun createPresenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
        lifecycle.addObserver(presenter)
        init()
    }

    protected open fun init() {}

    /**
     * 弹出加载中dialog
     *
     *
     * 如果需要带文字的dialog，重写此方法即可--参考-->LoginActivity
     */
    override fun showLoading() {
        if (null == loadingDialog) {
            loadingDialog = ProgressDialogUtil.getProgressDialog(this)
        }
        loadingDialog?.show()
        loadingDialog?.setText(null)
    }

    override fun showLoading(msg: String) {
        if (null == loadingDialog) {
            loadingDialog = ProgressDialogUtil.getProgressDialog(this)
        }
        loadingDialog?.show()
        loadingDialog?.setText(msg)
    }

    override fun dismissLoading() {
        ProgressDialogUtil.dismissDialog(loadingDialog)
    }

    override fun showToast(msg: String) {
        ToastUtil.showToast(msg)
    }

    override fun showToast(strId: Int) {
        ToastUtil.showToast(strId)
    }
}