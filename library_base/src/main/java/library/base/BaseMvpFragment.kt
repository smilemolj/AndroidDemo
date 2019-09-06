package library.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import library.base.utils.MyProgressDialog
import library.base.utils.ProgressDialogUtil
import library.base.utils.ToastUtil

/**
 * MVP模式Fragment的基类
 */
abstract class BaseMvpFragment<P : IBasePresenter> : Fragment(), IBaseView {

    protected lateinit var presenter: P

    @get:LayoutRes
    protected abstract val contentViewId: Int

    private var loadingDialog: MyProgressDialog? = null

    protected abstract fun createPresenter(): P

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(contentViewId, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = createPresenter()
        lifecycle.addObserver(presenter)
        init()
    }

    protected open fun init() {}

    override fun showLoading() {
        val context = context
        if (context != null && null == loadingDialog) {
            loadingDialog = ProgressDialogUtil.getProgressDialog(context)
        }
        loadingDialog?.show()
    }

    override fun showLoading(msg: String) {
        val context = context
        if (context != null && null == loadingDialog) {
            loadingDialog = ProgressDialogUtil.getProgressDialog(this.context!!)
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
