package com.yuehai.android.net

import library.net.ResponseVerify
import library.net.exception.ApiException
import org.json.JSONException
import org.json.JSONObject

/**
 * 校验json返回结果，统一拦截报异常
 * code==10000为成功
 *
 * Created by zhaoyuehai 2019/9/6
 */
class MyResponseVerify : ResponseVerify {

    @Throws(JSONException::class)
    override fun verify(response: String) {
        val jsonObject = JSONObject(response)
        if (jsonObject.has("code") && !jsonObject.isNull("code")) {
            val code = jsonObject.getString("code")
            if (code != "10000") {
                val message = jsonObject.getString("message")
                throw ApiException(code.toInt(), message)
            }
        }
    }
}