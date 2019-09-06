package library.net

import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer
import library.net.exception.ApiException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by zhaoyuehai 2019/3/29
 */
abstract class ResultObserver<T> constructor(private var container: DisposableContainer) :
    Observer<T> {
    private var disposable: Disposable? = null

    /**
     * 请求的开始，可以做显示Progress操作，并在onError方法 和 （onComplete方法 或者 onNext方法）取消Progress
     *
     * @param d disposable
     */
    override fun onSubscribe(@NonNull d: Disposable) {
        disposable = d
        container.add(disposable)
    }

    /**
     * 请求失败
     * onError 方法如果执行，onComplete方法将不会执行！
     */
    final override fun onError(e: Throwable) {
        when (e) {
            is ApiException -> //自定义异常
                onError(e.message ?: "请求失败")
            is ConnectException -> //手机没连上网的情况
                onError("网络异常，请检查网络后重试")
            is HttpException -> //404 500 服务器连接错误
                onError("服务器连接异常")
            is SocketTimeoutException -> //连上网，连接超时无响应
                onError("请求超时")
            is IOException ->//文件读写问题
                onError(e.message ?: "请求失败")
            else -> onError("请求失败")
        }
        clearDisposable()
    }

    /**
     * 请求失败
     */
    abstract fun onError(message: String)

    /**
     * 请求成功之后完成调用
     * onComplete 方法如果执行，onError方法将不会执行！
     */
    override fun onComplete() {
        clearDisposable()
    }

    /**
     * 移除并中止此Disposable
     */
    private fun clearDisposable() {
        if (disposable != null) {
            container.remove(disposable)
        }
    }
}
