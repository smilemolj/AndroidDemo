package com.yuehai.android.contract;

import androidx.annotation.Nullable;

import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yuehai.android.net.response.ResultBean;
import com.yuehai.android.net.response.UserForListBean;
import com.yuehai.android.widget.TipDialogFragment;

import java.util.List;

import library.base.IBasePresenter;
import library.base.IBaseView;

/**
 * 用户列表
 * Created by zhaoyuehai 2019/3/22
 */
public interface UserListContract {
    interface View extends IBaseView {
        /**
         * 展示列表数据
         *
         * @param result  列表数据
         * @param isClear 下拉刷新时，先清空
         */
        void showData(@Nullable ResultBean<List<UserForListBean>> result, boolean isClear);

        /**
         * 弹框确认后
         *
         * @param msg 提示信息
         */
        void alterConfirmDialog(String msg, TipDialogFragment.OnClickListener onClickListener);

        /**
         * 界面删除数据
         *
         * @param userBean UserBean
         */
        void onDeleteSuccess(UserForListBean userBean);
    }

    interface Presenter extends IBasePresenter, OnRefreshLoadMoreListener {
        /**
         * 弹框确认后删除
         */
        void onDeleteClick(UserForListBean userBean);
    }
}
