package com.home.template.films.storage;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.home.template.films.storage.model.Film;
import com.home.template.films.storage.model.User;
import com.home.template.films.storage.model.UserFilms;
import com.home.template.films.storage.model.UserWithFilms;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;


@Dao
public interface FilmsDaoJ {

    @Insert
    void insertAllFilms(@NotNull Collection<? extends Film> films);

    @Insert
    void insertAllFilms(Film... films);

    @Insert
    void insertAllUsers(@NotNull User userFirst, @NotNull User userSecond);

    @Query("SELECT * FROM users")
    List<User> getAllUser();

    @Delete
    void delete(Film film);

    @Query("SELECT * FROM films")
    List<Film> getAllFilm();

    @Query("SELECT * FROM films WHERE title LIKE :title")
    List<Film> getAllFilmWithTitle(String title);

    @Insert
    void insertUserFilms(UserFilms data);

    @Query("SELECT * FROM films INNER JOIN user_films ON films.userId = user_films.userId WHERE user_films.userId = :id")
    List<UserFilms> getAllFilmsForUser(Integer id);

    @Query("SELECT user.* FROM user, films WHERE films.userId=user.id")
    List<UserWithFilms> getAllUserWithAllFilms();

    @Query("SELECT * FROM user_films")
    List<UserFilms> getAllUserFilms();
}
