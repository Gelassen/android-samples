package com.home.template


import com.home.template.films.storage.FilmsRepository
import com.home.template.films.storage.model.Film

import io.reactivex.observers.DisposableObserver

class TestClass {

    fun start() {
        val repository = FilmsRepository(null!!, null!!, null!!)
        repository.getFilms().subscribe(object : DisposableObserver<List<Film>>() {
            override fun onNext(films: List<Film>) {

            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }
        })
    }
}
