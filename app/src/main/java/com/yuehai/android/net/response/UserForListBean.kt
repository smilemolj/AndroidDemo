package com.yuehai.android.net.response

import java.util.*

/**
 * Created by zhaoyuehai 2019/3/28
 */
class UserForListBean(var id: Long, var phone: String?, var email: String?, var nickName: String?) {
    var userName: String? = null
    var avatar: String? = null
    var status: Int = 0
    var createTime: Date? = null
    var updateTime: Date? = null
}
