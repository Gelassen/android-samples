package com.example.dkazakov.weather.network.parsers;

import android.content.ContentValues;
import android.util.JsonReader;

import com.example.dkazakov.weather.network.model.Forecast;
import com.example.dkazakov.weather.storage.Contract;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;


public class WeatherParser extends Parser {

    private HashMap<String, ElementParser> parsers = new HashMap<String, ElementParser>(2);
    private long cityId;

    public WeatherParser(LinkedList<ContentValues> values) {
        parsers.put(Forecast.CITY_TAG, new CityParser());
        parsers.put(Forecast.FORECAST_TAG, new WeatherListParser(values));
    }

    @Override
    public void parse(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            ElementParser parser = parsers.get(name);
            if (parser == null) {
                reader.skipValue();
            } else {
                parser.parse(reader);
                if (parser instanceof CityParser) {
                    cityId = ((CityParser) parser).getCityId();
                }

            }
        }
        reader.endObject();
    }


    private static abstract class ElementParser extends Parser {

        protected ContentValues row;

        public void setRow(ContentValues row) {
            this.row = row;
        }
    }

    private class CityParser extends ElementParser {

        private long cityId;

        public long getCityId() {
            return cityId;
        }

        @Override
        public void parse(JsonReader reader) throws IOException {
            reader.beginObject();
            while (reader.hasNext()) {
                final String name = reader.nextName();
                if (name.equals(Forecast.CITY_ID)) {
                    cityId = reader.nextLong();

                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        }
    }

    private class WeatherListParser extends ElementParser {

        private final HashMap<String, ElementParser> parsers = new HashMap<String, ElementParser>(2);

        private LinkedList<ContentValues> values;

        public WeatherListParser(LinkedList<ContentValues> values) {
            this.values = values;
            parsers.put(Forecast.TEMP_TAG, new TemperatureParser());
            parsers.put(Forecast.WEATHER_TAG, new WeatherDetailsParser());
        }

        @Override
        public void parse(JsonReader reader) throws IOException {
            reader.beginArray();
            while (reader.hasNext()) {

                ContentValues row = new ContentValues();
                row.put(Contract.Weather.CITY_ID, cityId);

                reader.beginObject();
                while (reader.hasNext()) {
                    final String name = reader.nextName();
                    ElementParser parser = parsers.get(name);
                    if (parser != null) {
                        parser.setRow(row);
                        parser.parse(reader);

                    } else if (name.equals(Forecast.DATE)){
                        final long date = reader.nextLong();
                        row.put(Contract.Weather.DAY, date);
                        row.put(Contract.Weather.ID, date);

                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
                values.add(row);
            }
            reader.endArray();
        }
    }

    private class TemperatureParser extends ElementParser {

        @Override
        public void parse(JsonReader reader) throws IOException {
            reader.beginObject();
            while (reader.hasNext()) {
                final String name = reader.nextName();
                if (name.equals(Forecast.DAY_TEMP)) {
                    row.put(Contract.Weather.DAY_TEMP, reader.nextDouble());

                } else if (name.equals(Forecast.MAX_TEMP)) {
                    row.put(Contract.Weather.MAX_TEMP, reader.nextDouble());

                } else if (name.equals(Forecast.MIN_TEMP)) {
                    row.put(Contract.Weather.MIN_TEMP, reader.nextDouble());

                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        }
    }

    private class WeatherDetailsParser extends ElementParser {

        @Override
        public void parse(JsonReader reader) throws IOException {
            // just get only first description
            boolean getOne = false;
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                while (reader.hasNext()) {
                    final String name = reader.nextName();
                    if (name.equals(Forecast.WEATHER_DESC) && !getOne) {
                        getOne = true;
                        row.put(Contract.Weather.DESC, reader.nextString());

                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
            }
            reader.endArray();
        }
    }

}
