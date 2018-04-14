package com.home.template

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.home.template.di.AppComponent
import com.home.template.films.storage.FilmsRepository
import com.home.template.films.storage.model.Film
import com.home.template.utils.AppDisposableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var disposables: CompositeDisposable

    @Inject
    lateinit var filmsRepository : FilmsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val app = (application as AppApplication).appComponent
        app.inject(this)

        disposables = CompositeDisposable()


//        filmsRepository.getFilms()
//                .subscribe(DisposableObserver() {
//
//                })
        val films = ArrayList<Film>()
        films.add(getFilm(10, "first"))
        films.add(getFilm(20, "second"))
        filmsRepository.insertFilms(films).subscribe(object: AppDisposableObserver<Unit>() {
            override fun onError(e: Throwable) {
                super.onError(e)
                Log.d(App.TAG, "On error", e)
            }

            override fun onNext(t: Unit) {
                super.onNext(t)
                Log.d(App.TAG, "On next")
            }
        })

        filmsRepository.getFilms().subscribe(object : AppDisposableObserver<List<Film>>() {
            override fun onNext(data: List<Film>) {
                super.onNext(data)
                Log.d(App.TAG, "Films: " + data.size)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                Log.e(App.TAG, "Films error", e)
            }
        })
    }

    fun getFilm(id: Int, title: String): Film {
        val film = Film()
        film.id = id
        film.title = title
        return film
    }
}
