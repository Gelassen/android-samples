package com.home.template.films.storage.model;


import android.arch.persistence.room.Embedded;

import java.util.List;

public class UserWithFilms {

    @Embedded
    User user;

    @Embedded
    List<Film> films;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}
