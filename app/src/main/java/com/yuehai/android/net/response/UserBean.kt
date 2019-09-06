package com.yuehai.android.net.response

/**
 * Created by zhaoyuehai 2019/3/28
 */
class UserBean {
    var accessToken: String? = null
    var refreshToken: String? = null
    var tokenHeader: String? = null
    var expiration: Long = 0

    var userName: String? = null
    var nickName: String? = null
    var phone: String? = null
    var email: String? = null
    var avatar: String? = null
    var status: Int = 0
}
