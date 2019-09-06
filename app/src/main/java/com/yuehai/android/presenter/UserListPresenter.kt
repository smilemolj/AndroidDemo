package com.yuehai.android.presenter

import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.yuehai.android.contract.UserListContract
import com.yuehai.android.net.ApiUtil
import com.yuehai.android.net.response.ResultBean
import com.yuehai.android.net.response.UserForListBean
import com.yuehai.android.widget.TipDialogFragment

import io.reactivex.disposables.Disposable
import library.base.BasePresenter
import library.net.ResultObserver
import library.net.util.RxUtil

/**
 * 用户列表 P
 * Created by zhaoyuehai 2019/3/22
 */
class UserListPresenter(view: UserListContract.View) : BasePresenter<UserListContract.View>(view),
    UserListContract.Presenter {

    private var pageNum = 1

    override fun onCreate() {
        super.onCreate()
        view?.showLoading()
        loadData()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageNum++
        loadData()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageNum = 1
        loadData()
    }

    private fun loadData() {
        ApiUtil.instance
            .apiService
            .getUsers(pageNum, 10)
            .compose(RxUtil.io_main())
            .subscribe(object : ResultObserver<ResultBean<List<UserForListBean>>>(this) {

                override fun onNext(listResultBean: ResultBean<List<UserForListBean>>) {
                    view?.dismissLoading()
                    if (listResultBean.isSuccess) {
                        view?.showData(listResultBean, pageNum == 1)
                        if ((listResultBean.data.size == 0) and (pageNum > 1))
                            pageNum--
                    } else {
                        loadFail(listResultBean.message)
                    }
                }

                private fun loadFail(msg: String) {
                    view?.showToast(msg)
                    view?.showData(null, pageNum == 1)
                    if (pageNum > 1) pageNum--
                }

                override fun onError(message: String) {
                    view?.dismissLoading()
                    loadFail(message)
                }

            })
    }

    override fun onDeleteClick(userBean: UserForListBean) {
        view?.alterConfirmDialog(
            "确定要删除 " + userBean.userName + " 吗？",
            object : TipDialogFragment.OnClickListener {
                override fun onConfirm() {
                    delete(userBean)
                }

                override fun onCancel() {

                }
            })
    }

    private fun delete(userBean: UserForListBean) {
        ApiUtil.instance
            .apiService
            .deleteUser(userBean.id)
            .compose(RxUtil.io_main())
            .subscribe(object : ResultObserver<ResultBean<Any>>(this) {
                override fun onSubscribe(d: Disposable) {
                    super.onSubscribe(d)
                    view?.showLoading()
                }

                override fun onNext(stringResultBean: ResultBean<Any>) {
                    view?.dismissLoading()
                    view?.showToast(stringResultBean.message)
                    if (stringResultBean.isSuccess) {
                        view?.onDeleteSuccess(userBean)
                    }
                }

                override fun onError(message: String) {
                    view?.dismissLoading()
                    view?.showToast(message)
                }
            })
    }
}
