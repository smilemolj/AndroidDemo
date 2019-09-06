package com.yuehai.android

import com.yuehai.android.net.response.UserBean
import com.yuehai.android.util.LogUtil
import com.yuehai.android.util.SPUtil
import java.util.*

class UserData private constructor() {

    private object ClassHolder {
        val INSTANCE = UserData()
    }

    companion object {
        private val IS_LOGIN = "SP_IS_LOGIN"

        private val TOKEN = "SP_USER_TOKEN"
        private val REFRESH_TOKEN = "SP_USER_REFRESH_TOKEN"
        private val TOKEN_HEADER = "SP_USER_TOKEN_HEADER"
        private val EXPIRATION = "SP_USER_EXPIRATION"
        private val USER_NAME = "SP_USER_USER_NAME"
        private val NICK_NAME = "SP_USER_NICK_NAME"
        private val PHONE = "SP_USER_PHONE"
        private val EMAIL = "SP_USER_EMAIL"
        private val AVATAR = "SP_USER_AVATAR"
        private val STATUS = "SP_USER_STATUS"

        val instance: UserData
            get() = ClassHolder.INSTANCE
    }

    private var user: UserBean? = null

    fun getUser(): UserBean? {
        if (user == null) {
            val spUtil = SPUtil.getInstance(Contacts.SP_NAME)
            val isLogin = spUtil.getBoolean(IS_LOGIN, false)
            if (isLogin) {
                user = UserBean()
                user!!.accessToken = spUtil.getString(TOKEN)
                user!!.refreshToken = spUtil.getString(REFRESH_TOKEN)
                user!!.tokenHeader = spUtil.getString(TOKEN_HEADER)
                user!!.expiration = spUtil.getLong(EXPIRATION)
                user!!.userName = spUtil.getString(USER_NAME)
                user!!.nickName = spUtil.getString(NICK_NAME)
                user!!.phone = spUtil.getString(PHONE)
                user!!.email = spUtil.getString(EMAIL)
                user!!.avatar = spUtil.getString(AVATAR)
                user!!.status = spUtil.getInt(STATUS, -1)
            }
        }
        return user
    }

    fun saveUser(userBean: UserBean) {
        this.user = userBean
        LogUtil.e("======保存的过期时间是：" + Date(user!!.expiration))
        SPUtil.getInstance(Contacts.SP_NAME)
            .edit
            .putBoolean(IS_LOGIN, true)
            .putString(TOKEN, userBean.accessToken)
            .putString(REFRESH_TOKEN, userBean.refreshToken)
            .putString(TOKEN_HEADER, userBean.tokenHeader)
            .putLong(EXPIRATION, userBean.expiration)
            .putString(USER_NAME, userBean.userName)
            .putString(NICK_NAME, userBean.nickName)
            .putString(PHONE, userBean.phone)
            .putString(EMAIL, userBean.email)
            .putString(AVATAR, userBean.avatar)
            .putInt(STATUS, userBean.status)
            .commit()
    }

    fun clearUser() {
        SPUtil.getInstance(Contacts.SP_NAME)
            .edit
            .putBoolean(IS_LOGIN, false)
            .remove(TOKEN)
            .remove(TOKEN_HEADER)
            .remove(USER_NAME)
            .remove(NICK_NAME)
            .remove(PHONE)
            .remove(EMAIL)
            .remove(AVATAR)
            .remove(STATUS)
            .commit()
        this.user = null
    }
}
