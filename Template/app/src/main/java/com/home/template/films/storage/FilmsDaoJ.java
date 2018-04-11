package com.home.template.films.storage;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.home.template.films.storage.model.Film;
import com.home.template.films.storage.model.FilmWithMeta;
import com.home.template.films.storage.model.Meta;
import com.home.template.films.storage.model.UserWithFilms;

import java.util.List;

@Dao
public interface FilmsDaoJ {

    @Insert
    void insertAllFilms(Film... films);

    @Insert
    void insertAllMeta(Meta... metas);

    @Delete
    void delete(Film film);

    @Query("SELECT * FROM films")
    List<Film> getAllFilm();

    @Query("SELECT * FROM films WHERE title LIKE :title")
    List<Film> getAllFilmWithTitle(String title);

    @Query("SELECT meta.*, films.* FROM meta INNER JOIN films ON films.title=meta.name")
    List<FilmWithMeta> getAllFilmsWithMeta();

    @Query("SELECT user.* FROM user, films WHERE films.userId=user.id")
    List<UserWithFilms> getAllUserWithAllFilms();
}
