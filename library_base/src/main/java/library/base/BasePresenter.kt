package library.base


import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * MVP中的Presenter基类
 *
 *
 * 使用了[CompositeDisposable]维护RxJava订阅
 * 也可以考虑用[io.reactivex.internal.disposables.DisposableHelper]
 *
 *
 */
abstract class BasePresenter<V>(view: V) : IBasePresenter {

    //网络请求订阅管理容器
    private var mCompositeDisposable: CompositeDisposable

    //防止view的内存泄露，弱引用
    private var mViewRef: Reference<V>//View接口类型的弱引用

    init {
        mCompositeDisposable = CompositeDisposable()
        mViewRef = WeakReference(view) //建立关联
    }

    //获取View
    protected val view: V? get() = mViewRef.get()

    /**
     * 在Activity/Fragment的onCreate()方法执行完成后执行此方法
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected open fun onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected open fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected open fun onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected open fun onDestroy() {
        mViewRef.clear() //解除关联
        mCompositeDisposable.clear()
    }

    override fun add(d: Disposable): Boolean {
        //如果解绑了的话添加,需要新的实例否则绑定时无效的
        if (mCompositeDisposable.isDisposed) {
            mCompositeDisposable = CompositeDisposable()
        }
        return mCompositeDisposable.add(d)
    }

    /**
     * Removes and disposes the given disposable if it is part of this
     * container.
     * 执行delete(Disposable d)后，并调用该Disposable的dispose()方法
     * 先从容器中删掉，不再持有，然后去中止他。
     *
     * @param d the disposable to remove and dispose, not null
     * @return true if the operation was successful
     */
    override fun remove(d: Disposable): Boolean {
        //如果解绑了的话添加,需要新的实例否则绑定时无效的
        if (mCompositeDisposable.isDisposed) {
            mCompositeDisposable = CompositeDisposable()
        }
        return mCompositeDisposable.remove(d)
    }

    /**
     * Removes (but does not dispose) the given disposable if it is part of this
     * container.
     * 从容器【OpenHashSet<Disposable>】中删除此引用,不会调用该Disposable的dispose()方法）
     * 只是从容器中删掉，不再持有，不会去中止他。
     *
     * @param d the disposable to remove, not null
     * @return true if the operation was successful
    </Disposable> */
    override fun delete(d: Disposable): Boolean {
        //如果解绑了的话添加,需要新的实例否则绑定时无效的
        if (mCompositeDisposable.isDisposed) {
            mCompositeDisposable = CompositeDisposable()
        }
        return mCompositeDisposable.delete(d)
    }
}
