package com.home.template.storage

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.home.template.films.response.Film

@Database(entities = arrayOf(Film::class), version = 1)
abstract class FilmsDatabase : RoomDatabase() {
    abstract fun getFilmsDao() : FilmsDao
}