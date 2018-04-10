package com.home.template.utils

import io.reactivex.Observable

class TransformUtil {

    fun <T, E : Transformable<T>> transformCollection(data: List<E>): List<T> {
        return Observable.fromIterable(data)
                .map { item -> item.transform() }
                .toList()
                .blockingGet()
    }

    fun <T, E : Transformable<T>> transformCollectionWithObservable(data: List<E>): Observable<List<T>> {
        return Observable.fromIterable(data)
                .map { item -> item.transform() }
                .toList()
                .toObservable()
    }
}