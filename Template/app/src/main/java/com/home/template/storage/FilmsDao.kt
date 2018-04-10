package com.home.template.storage

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.home.template.films.response.Film

@Dao
interface FilmsDao {

    @Insert
    fun insertAll(vararg films: Film)

    // Удаление Person из бд
    @Delete
    fun delete(film: Film)

    // Получение всех Person из бд
    @Query("SELECT * FROM films")
    fun getAllFilm(): List<Film>

    // Получение всех Person из бд с условием
    @Query("SELECT * FROM films WHERE title LIKE :tile")
    fun getAllFilmWithTitle(title: String): List<Film>
}