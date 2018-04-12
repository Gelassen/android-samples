package com.home.template.di

import android.content.Context
import com.home.template.films.storage.FilmsDatabaseJ
import com.home.template.films.storage.FilmsRepository
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton


@Module
class SchedulersModule
constructor(context: Context){

    @Provides
    @Singleton
    fun providesFilmRepository(
            @Named("ui") uiScheduler: Scheduler,
            @Named("io") ioScheduler: Scheduler,
            databaseJ: FilmsDatabaseJ) : FilmsRepository {
        return FilmsRepository(ioScheduler, uiScheduler, databaseJ)
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(context : Context) : FilmsDatabaseJ {
        return FilmsDatabaseJ.getInstance(context)
    }

    @Provides
    @Named("io")
    fun provideIoScheduler() : Scheduler {
        return Schedulers.io()
    }

    @Provides
    @Named("ui")
    fun provideUiScheduler() : Scheduler {
        return AndroidSchedulers.mainThread()
    }
}