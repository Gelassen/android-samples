package com.home.template.films.storage.model;


import android.arch.persistence.room.Embedded;

public class FilmWithMeta {

    @Embedded
    Film film;

    @Embedded
    Meta meta;

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
