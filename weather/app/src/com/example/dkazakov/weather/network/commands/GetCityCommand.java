package com.example.dkazakov.weather.network.commands;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;
import android.util.Log;

import com.example.dkazakov.weather.R;
import com.example.dkazakov.weather.Weather;
import com.example.dkazakov.weather.network.HttpCommand;
import com.example.dkazakov.weather.network.parsers.CityParser;
import com.example.dkazakov.weather.network.parsers.WeatherParser;
import com.example.dkazakov.weather.storage.Contract;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;


public class GetCityCommand extends HttpCommand {

    private String city;

    public GetCityCommand(String city) {
        this.city = city;
    }

    //http://api.openweathermap.org/data/2.5/find?q=London&type=like&mode=json
    @Override
    protected void createRequest() {
        Uri.Builder builder = Uri.parse(context.getString(R.string.url)).buildUpon();
        builder.appendEncodedPath(context.getString(R.string.url_find_city_part));
        builder.appendQueryParameter("q", city);
        builder.appendQueryParameter("type", "like");
        builder.appendQueryParameter("mode", "json");

        httpUriRequest = new HttpGet(builder.toString());
        addApiKeyHeader(httpUriRequest);
    }

    @Override
    protected void processRequest(HttpResponse response) {
        try {
//            Log.d(Weather.TAG, EntityUtils.toString(response.getEntity()));
            JsonReader reader = new JsonReader(new InputStreamReader(response.getEntity().getContent()));
            LinkedList<ContentValues> cities = new LinkedList<ContentValues>();
            new CityParser(cities).parse(reader);

            ContentValues[] citiesValues = new ContentValues[cities.size()];
            cities.toArray(citiesValues);
            Uri citiesUri = Contract.contentUri(Contract.CitiesForChoose.class);
            context.getContentResolver().delete(citiesUri, null, null);
            context.getContentResolver().bulkInsert(citiesUri, citiesValues);
        } catch (IOException e) {
            Log.d(Weather.TAG, "Failed to obtain weather data", e);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
    }

    public final static Parcelable.Creator<GetCityCommand> CREATOR = new Parcelable.Creator<GetCityCommand>() {

        @Override
        public GetCityCommand[] newArray(int size) {
            return new GetCityCommand[size];
        }

        @Override
        public GetCityCommand createFromParcel(Parcel source) {
            return new GetCityCommand(
                    source.readString()
            );
        }
    };
}
