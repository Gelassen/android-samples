package com.home.template.films.storage.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "meta",
        indices = @Index(value = {"filmsId", "name"}),
        foreignKeys = @ForeignKey(
                entity = Film.class,
                parentColumns = "id",
                childColumns = "name")
)
public class Meta {
    @PrimaryKey
    @NonNull
    private String name;
    private String desc;

    private Integer filmsId;

    public Meta() {
        this.filmsId = 0;
    }

    public Integer getFilmsId() {
        return filmsId;
    }

    public void setFilmsId(Integer filmsId) {
        this.filmsId = filmsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
