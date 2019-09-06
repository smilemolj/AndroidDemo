package library.net.exception

/**
 * 自定义异常
 * Created by zhaoyuehai 2018/7/30
 */
class ApiException(
    val code: Int, message: String?
) : RuntimeException(message) {
    companion object {
        const val JSON_EXCEPTION = 20001
        const val DOWNLOAD_EXCEPTION = 20002
        const val UPLOAD_EXCEPTION = 20003
    }
}