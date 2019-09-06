package com.yuehai.android.presenter

import com.yuehai.android.contract.SetPwdContract

import library.base.BasePresenter

/**
 * SetPwd P
 */
class SetPwdPresenter(view: SetPwdContract.View) : BasePresenter<SetPwdContract.View>(view),
    SetPwdContract.Presenter
