package com.home.vkphotos.photos.preview;


import android.util.Log;

import com.google.gson.Gson;
import com.home.vkphotos.App;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonToResponseConverter {

    public Response convert(JSONObject json) {
        Response response = new Response();
        try {
            JSONObject resp = (JSONObject) json.get("response");
            response = new Gson().fromJson(resp.toString(), Response.class);
        } catch (JSONException e) {
            Log.e(App.TAG, "Failed to obtain the response", e);
        }
        return response;
    }
}
