package com.home.vkphotos.photos.preview;


import com.google.gson.Gson;

public class ResponseConverter<T> {

    public Response convert(String json, Response type) {
        return new Gson().fromJson(json, Response.class);
    }
}
