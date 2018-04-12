package com.home.template.films.storage.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(
        tableName = "user_films",
        primaryKeys = { "userId", "filmsId" },
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        childColumns = "userId",
                        parentColumns = "id"
                ),
                @ForeignKey(
                        entity = Film.class,
                        childColumns = "filmsId",
                        parentColumns = "id"
                )
        }
)
public class UserFilms {
    private int userId;
    private int filmsId;

    public UserFilms(int userId, int filmsId) {
        this.userId = userId;
        this.filmsId = filmsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFilmsId() {
        return filmsId;
    }

    public void setFilmsId(int filmsId) {
        this.filmsId = filmsId;
    }
}
