package com.home.template.utils

import io.reactivex.observers.DisposableObserver


abstract class AppDisposableObserver<T> : DisposableObserver<T>() {
    override fun onError(e: Throwable) {}

    override fun onComplete() {}

    override fun onNext(t: T) {}

}