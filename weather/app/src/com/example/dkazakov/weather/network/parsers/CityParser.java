package com.example.dkazakov.weather.network.parsers;

import android.content.ContentValues;
import android.util.JsonReader;

import com.example.dkazakov.weather.network.model.City;
import com.example.dkazakov.weather.storage.Contract;

import java.io.IOException;
import java.util.LinkedList;

public class CityParser extends Parser {

    private LinkedList<ContentValues> values;

    public CityParser(LinkedList<ContentValues> values) {
        this.values = values;
    }

    @Override
    public void parse(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if (name.equals(City.CITIES_TAG)) {

                reader.beginArray();
                while (reader.hasNext()) {
                    reader.beginObject();
                    parseCitiy(reader, values);
                    reader.endObject();
                }
                reader.endArray();

            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    private void parseCitiy(JsonReader reader, LinkedList<ContentValues> values) throws IOException {
        ContentValues row = new ContentValues(5);
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if (name.equals(City.CITY_ID)) {
                row.put(Contract.CitiesForChoose.ID, reader.nextLong());

            } else if (name.equals(City.NAME)) {
                row.put(Contract.CitiesForChoose.CITY, reader.nextString());

            }  else if (name.equals(City.COORDINATION_TAG)) {

                reader.beginObject();
                while (reader.hasNext()) {
                    final String coordName = reader.nextName();
                    if (coordName.equals(City.LATITUDE)) {
                        row.put(Contract.CitiesForChoose.LAT, reader.nextDouble());

                    } else if (coordName.equals(City.LONGITUDE)) {
                        row.put(Contract.CitiesForChoose.LONG, reader.nextDouble());

                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();

            } else if (name.equals(City.COUNTRY_TAG)) {

                reader.beginObject();
                while (reader.hasNext()) {
                    final String countryName = reader.nextName();
                    if (countryName.equals(City.COUNTRY)) {
                        row.put(Contract.CitiesForChoose.COUNTRY, reader.nextString());

                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();

            } else {
                reader.skipValue();
            }
        }
        values.add(row);
    }

}
