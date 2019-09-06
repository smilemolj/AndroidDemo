package library.net

/**
 * Json响应结果校验
 * Created by zhaoyuehai 2019/9/6
 */
interface ResponseVerify {
    fun verify(response: String)
}