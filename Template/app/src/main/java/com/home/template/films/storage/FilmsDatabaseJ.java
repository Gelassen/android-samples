package com.home.template.films.storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import com.home.template.films.storage.converters.Converters;
import com.home.template.films.storage.model.Film;
import com.home.template.films.storage.model.User;
import com.home.template.films.storage.model.UserFilms;

import org.jetbrains.annotations.NotNull;


@TypeConverters({Converters.class})
@Database(entities = {Film.class, User.class, UserFilms.class}, version = 1)
public abstract class FilmsDatabaseJ extends RoomDatabase {

    private static final String DATABASE_NAME = "DATABASE_NAME";

    private static FilmsDatabaseJ instance;

    public abstract FilmsDaoJ getFilmsDao();


    @NotNull
    public static FilmsDatabaseJ getInstance(Context context) {
        if (instance == null) {
            synchronized (instance) {
                if (instance != null) return
                instance = Room.databaseBuilder(context, FilmsDatabaseJ.class, DATABASE_NAME).build();
            }
        }
        return instance;
    }
}
