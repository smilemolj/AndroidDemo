package com.yuehai.android.contract

import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.yuehai.android.net.response.UserForListBean
import com.yuehai.android.widget.TipDialogFragment
import library.base.IBasePresenter
import library.base.IBaseView

/**
 * 用户列表
 * Created by zhaoyuehai 2019/3/22
 */
interface UserListContract {
    interface View : IBaseView {
        /**
         * 展示列表数据
         *
         * @param result  列表数据
         * @param isClear 下拉刷新时，先清空
         */
        fun showData(data: List<UserForListBean>?, isRefresh: Boolean)

        /**
         * 弹框确认后
         *
         * @param msg 提示信息
         */
        fun alterConfirmDialog(msg: String, listener: TipDialogFragment.OnClickListener)

        /**
         * 界面删除数据
         *
         * @param userBean UserBean
         */
        fun onDeleteSuccess(userBean: UserForListBean)
    }

    interface Presenter : IBasePresenter, OnRefreshLoadMoreListener {
        /**
         * 弹框确认后删除
         */
        fun onDeleteClick(userBean: UserForListBean)
    }
}
