package com.home.template.films.storage.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.home.template.films.storage.model.UserWithFilms;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {

    @TypeConverter
    public static ArrayList<Integer> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Integer>>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Integer> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static ArrayList<UserWithFilms> fromSource(String value) {
        Type listType = new TypeToken<ArrayList<UserWithFilms>>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromUserWithFilmsArrayList(ArrayList<UserWithFilms> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
