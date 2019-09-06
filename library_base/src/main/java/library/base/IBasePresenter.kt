package library.base


import androidx.lifecycle.LifecycleObserver

import io.reactivex.internal.disposables.DisposableContainer


interface IBasePresenter : DisposableContainer, LifecycleObserver
