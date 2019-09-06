package com.yuehai.android.net.interceptor

import android.content.Intent
import com.yuehai.android.UserData
import com.yuehai.android.net.ApiUtil
import com.yuehai.android.net.response.ResultBean
import com.yuehai.android.net.response.UserBean
import com.yuehai.android.ui.LoginActivity
import com.yuehai.android.ui.RegisterActivity
import com.yuehai.android.ui.SetPwdActivity
import com.yuehai.android.util.LogUtil
import library.base.BaseApplication
import library.net.exception.ApiException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

class TokenInterceptor : Interceptor {

    companion object {
        const val HEADER_NO_TOKEN = "NeedToken: false"
        const val HEADER_NEED_TOKEN = "NeedToken: true"
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original
            .newBuilder()
            .addHeader("Accept", "application/json")
        //部分接口需要Token
        val needToken = original.header("NeedToken")
        if (needToken == null || needToken == "true") {//NeedToken为null时默认添加token
            val token = getToken()
            if (token != null) {
                builder.header("Authorization", token)
            } else {
                clearAndLogin()
                throw ApiException(401, "需要重新登录")
            }
        }
        if (needToken != null) {
            builder.removeHeader("NeedToken")
        }
        val request = builder
            .method(original.method(), original.body())
            .build()
        return chain.proceed(request)//一定注意此处：return一定要这样写！因为后续拦截器回进行实际的请求操作即：proceed(request)
    }

    @Synchronized
    private fun getToken(): String? {
        var token: String? = null
        if (UserData.getInstance().user == null) {
            return null
        }
        val expiration =
            UserData.getInstance().user.expiration - 10 * 60 * 1000 //提前10分钟（后端正式配置8小时过期，提前10分钟刷新token即可）
        LogUtil.i("======过期时间是：" + Date(expiration).toString())
        LogUtil.i("======当前时间是：" + Date(System.currentTimeMillis()).toString())
        //本地判断token过期
        if (Date(expiration).before(Date())) {
            LogUtil.e("已过期，需要去刷新token")
            val call = ApiUtil.instance
                .apiService
                .refreshToken(UserData.getInstance().user.refreshToken)
            var resultBean: ResultBean<UserBean>? = null
            try {
                resultBean = call.execute().body()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (resultBean != null && resultBean.code == "10000" && resultBean.data != null) {
                    LogUtil.e("======刷新token成功")
                    UserData.getInstance().saveUser(resultBean.data)
                    token =
                        UserData.getInstance().user.tokenHeader + UserData.getInstance().user.accessToken
                }
            }
        } else {
            LogUtil.i("======token未过期")
            token =
                UserData.getInstance().user.tokenHeader + UserData.getInstance().user.accessToken
        }
        return token
    }

    private val isLoginPage: Boolean
        get() {
            val topActivity = BaseApplication.instance.topActivity ?: return false
            return (topActivity.localClassName.endsWith(LoginActivity::class.java.simpleName)
                    || topActivity.localClassName.endsWith(RegisterActivity::class.java.simpleName)
                    || topActivity.localClassName.endsWith(SetPwdActivity::class.java.simpleName))
        }

    @Synchronized
    private fun clearAndLogin() {
        if (isLoginPage) return
        ApiUtil.instance.cancelAll()
        UserData.getInstance().clearUser()
        BaseApplication.instance.startActivity(
            Intent(
                BaseApplication.instance,
                LoginActivity::class.java
            )
        )
    }
}
