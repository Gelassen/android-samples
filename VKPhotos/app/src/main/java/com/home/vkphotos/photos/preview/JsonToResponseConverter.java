package com.home.vkphotos.photos.preview;


import com.google.gson.Gson;
import org.json.JSONObject;

public class JsonToResponseConverter {

    public Response convert(JSONObject json) {
        Response response;
        JSONObject resp = json.optJSONObject("response");
        response = new Gson().fromJson(resp.toString(), Response.class);
        return response;
    }
}
