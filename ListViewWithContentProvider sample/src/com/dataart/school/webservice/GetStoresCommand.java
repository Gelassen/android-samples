package com.dataart.school.webservice;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.util.Log;

import com.dataart.school.CustomContentProvider;
import com.dataart.school.R;
import com.dataart.school.Schema;
import com.dataart.school.Schema.Stores;

public class GetStoresCommand extends HttpCommand {

    @Override
    protected HttpUriRequest getRequest() {
        String url = context.getString(R.string.url);
        HttpGet request = new HttpGet(url);
        request.setHeader("Content-Type", "application/json; encoding=utf-8");
        return request;
    }

    @Override
    protected void handleResult(String json) {
        Log.d(Schema.TAG, json);
        
        try {
            JSONArray content = new JSONArray(json);
            final int count = content.length();
//            ContentValues[] stores = new ContentValues[count];
            for (int i = 0; i < count; i++) {
                JSONObject store = content.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put(Stores.STORE_NAME, store.getString("name"));
                values.put(Stores.ID, store.getString("id"));
                values.put(Stores.ADDRESS, store.getString("address"));
                values.put(Stores.PHONE, store.getString("phone"));
                
                JSONObject location = store.getJSONObject("location");
                values.put(Stores.LATITUDE, location.getString("latitude"));
                values.put(Stores.LONGITUDE, location.getString("longitude"));
                // Approach #1
                context.getContentResolver().insert(CustomContentProvider.STORES_URI, values);
//                stores[i] = values;
            }
            // Approach #2
//            context.getContentResolver().bulkInsert(CustomContentProvider.STORES_URI, stores);
        } catch (JSONException e) {
            Log.d(Schema.TAG, "Failed to obtain json", e);
        }
    }

}
