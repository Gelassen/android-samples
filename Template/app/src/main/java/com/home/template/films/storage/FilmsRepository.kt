package com.home.template.films.storage

import com.home.template.films.storage.model.Film
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named


class FilmsRepository @Inject
constructor(
        @Named("io") var ioScheduler: Scheduler,
        @Named("ui") var uiScheduler: Scheduler,
        var database: FilmsDatabaseJ
){

    fun insertFilms(films : Collection<Film>): Observable<Unit> {
        return Observable.fromCallable{database.filmsDao.insertAllFilms(films)}
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
    }

    fun getFilms() : Observable<MutableList<Film>> {
        return Observable.fromCallable{ database.filmsDao.allFilms }
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
    }
}
