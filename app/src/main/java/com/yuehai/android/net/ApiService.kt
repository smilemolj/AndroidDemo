package com.yuehai.android.net

import com.yuehai.android.net.interceptor.TokenInterceptor
import com.yuehai.android.net.request.RegisterUserBean
import com.yuehai.android.net.response.ResultBean
import com.yuehai.android.net.response.UserBean
import com.yuehai.android.net.response.UserForListBean
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by zhaoyuehai 2019/3/29
 */
interface ApiService {
    /**
     * 登录接口
     *
     * @param userName 用户名/手机号
     * @param password 密码
     * @return Observable
     */
    @FormUrlEncoded
    @Headers(TokenInterceptor.HEADER_NO_TOKEN)
    @POST("api/v1/login")
    fun login(@Field("username") userName: String, @Field("password") password: String): Observable<ResultBean<UserBean>>

    /**
     * 刷新token接口（同步请求，在拦截器内执行）
     *
     * @return Call
     */
    @FormUrlEncoded
    @Headers(TokenInterceptor.HEADER_NO_TOKEN)
    @POST("api/v1/token")
    fun refreshToken(@Field("refreshToken") refreshToken: String): Call<ResultBean<UserBean>>

    /**
     * 注册
     */
    @Headers(TokenInterceptor.HEADER_NO_TOKEN)
    @POST("api/v1/user")
    fun register(@Body body: RegisterUserBean): Observable<ResultBean<Long>>

    /**
     * 删除用户
     */
    @DELETE("api/v1/user/{id}")
    fun deleteUser(@Path("id") id: Long?): Observable<ResultBean<Any>>

    /**
     * 修改用户
     */
    @PUT("api/v1/user")
    fun updateUser(@Body body: UserForListBean): Observable<ResultBean<Any>>

    /**
     * 查询用户列表【分页】
     */
    @Headers(TokenInterceptor.HEADER_NEED_TOKEN)//可以省略此header
    @GET("api/v1/users")
    fun getUsers(@Query("pageNum") pageNum: Int, @Query("pageSize") pageSize: Int): Observable<ResultBean<List<UserForListBean>>>
}
