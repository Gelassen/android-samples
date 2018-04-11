package com.home.template.films.storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.home.template.films.storage.converters.Converters;
import com.home.template.films.storage.model.Film;
import com.home.template.films.storage.model.Meta;
import com.home.template.films.storage.model.User;


@TypeConverters({Converters.class})
@Database(entities = {Film.class, User.class, Meta.class}, version = 1)
public abstract class FilmsDatabaseJ extends RoomDatabase {

    public abstract FilmsDaoJ getFilmsDao();
}
