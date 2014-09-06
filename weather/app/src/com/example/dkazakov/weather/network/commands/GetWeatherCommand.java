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
import com.example.dkazakov.weather.network.parsers.WeatherParser;
import com.example.dkazakov.weather.storage.Contract;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class GetWeatherCommand extends HttpCommand {

    private String city;
    private int count;

    public GetWeatherCommand(String city, int count) {
        this.city = city;
        this.count = count;
    }

    //http://api.openweathermap.org/data/2.5/forecast/daily?q=London&mode=json&units=metric&cnt=7
    @Override
    protected void createRequest() {
        Uri.Builder builder = Uri.parse(context.getString(R.string.url)).buildUpon();
        builder.appendEncodedPath(context.getString(R.string.url_weather_part));
        builder.appendQueryParameter("q", city);
        builder.appendQueryParameter("mode", "json");
        builder.appendQueryParameter("units", "metric");
        builder.appendQueryParameter("count", Integer.toString(count));

        httpUriRequest = new HttpGet(builder.toString());
        addApiKeyHeader(httpUriRequest);
    }

    @Override
    protected void processRequest(HttpResponse response) {
        try {
//            Log.d(Weather.TAG, EntityUtils.toString(response.getEntity()));
            JsonReader reader = new JsonReader(new InputStreamReader(response.getEntity().getContent()));
            LinkedList<ContentValues> forecast = new LinkedList<ContentValues>();
            new WeatherParser(forecast).parse(reader);

            ContentValues[] forecastValues = new ContentValues[forecast.size()];
            forecast.toArray(forecastValues);
            Uri weatherUri = Contract.contentUri(Contract.Weather.class);
            context.getContentResolver().bulkInsert(weatherUri, forecastValues);
        } catch (IOException e) {
            Log.d(Weather.TAG, "Failed to obtain weather data", e);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeInt(count);
    }

    public final static Parcelable.Creator<GetWeatherCommand> CREATOR = new Parcelable.Creator<GetWeatherCommand>() {

        @Override
        public GetWeatherCommand[] newArray(int size) {
            return new GetWeatherCommand[size];
        }

        @Override
        public GetWeatherCommand createFromParcel(Parcel source) {
            return new GetWeatherCommand(
                    source.readString(),
                    source.readInt()
            );
        }
    };
}
