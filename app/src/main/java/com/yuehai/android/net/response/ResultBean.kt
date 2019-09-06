package com.yuehai.android.net.response

/**
 * Created by zhaoyuehai 2019/3/28
 */
class ResultBean<T>(
    var code: String,
    var message: String,
    var serviceCode: String,
    var data: T? = null
)
//    {"code":"10000","message":"success","serviceCode":"1.0","data":[
