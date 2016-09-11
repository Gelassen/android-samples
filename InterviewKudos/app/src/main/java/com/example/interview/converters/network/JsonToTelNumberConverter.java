package com.example.interview.converters.network;

import android.util.Log;

import com.example.interview.App;
import com.example.interview.converters.IConverter;
import com.example.interview.model.TelNumber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The intent of this class is encapsualte details of conversion from json to
 * Tel number object. For simple use case it is ok to use out-of-bix classes, but
 * in case of growth it is better to add special tools, e.g. gson and rewrite this logic
 *
 * Created by John on 9/11/2016.
 */
public class JsonToTelNumberConverter implements IConverter<JSONArray, List<TelNumber>> {

    @Override
    public List<TelNumber> convert(JSONArray input) {
        if (input == null || input.length() == 0) return new ArrayList<>();

        List<TelNumber> result = new ArrayList<>();
        try {
            final int size = input.length();
            for (int idx = 0; idx < size; idx++) {
                TelNumber telNumber = null;
                telNumber = parse(input.getJSONObject(idx));
                result.add(telNumber);
            }
        } catch (JSONException e) {
            Log.e(App.TAG, "Failed to obtain tel number from json", e);
        }
        return result;
    }
    private TelNumber parse(final JSONObject json) throws JSONException {
        TelNumber result = new TelNumber();
        result.setPhoneNumber(json.getString("phoneNumber"));
        result.setPhoneNumberPrice(json.getString("phoneNumberPrice"));
        result.setPhoneNumberOwner(json.getString("phoneNumberOwner"));
        return result;
    }
}
