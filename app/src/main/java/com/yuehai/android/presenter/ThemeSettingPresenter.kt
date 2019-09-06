package com.yuehai.android.presenter

import com.yuehai.android.contract.ThemeSettingContract

import library.base.BasePresenter

/**
 * ThemeSetting P
 */
class ThemeSettingPresenter(view: ThemeSettingContract.View) :
    BasePresenter<ThemeSettingContract.View>(view), ThemeSettingContract.Presenter
