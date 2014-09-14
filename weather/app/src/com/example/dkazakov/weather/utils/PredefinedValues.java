package com.example.dkazakov.weather.utils;

import android.content.ContentValues;

import com.example.dkazakov.weather.storage.Contract;

public class PredefinedValues {

    public static ContentValues getDublinAsValues() {
        final ContentValues values = new ContentValues(5);
        values.put(Contract.Cities.ID, 2964574);
        values.put(Contract.Cities.CITY, "Dublin");
        values.put(Contract.Cities.COUNTRY, "IE");
        values.put(Contract.Cities.LAT, 53.34399);
        values.put(Contract.Cities.LONG, -6.26719);
        return values;
    }

    public static ContentValues getLondonAsValues() {
        final ContentValues values = new ContentValues(5);
        values.put(Contract.Cities.ID, 2643743);
        values.put(Contract.Cities.CITY, "London");
        values.put(Contract.Cities.COUNTRY, "GB");
        values.put(Contract.Cities.LAT, 51.50853);
        values.put(Contract.Cities.LONG, -0.12574);
        return values;
    }

    public static ContentValues getNewYorkAsValues() {
        final ContentValues values = new ContentValues(5);
        values.put(Contract.Cities.ID, 5128581);
        values.put(Contract.Cities.CITY, "New York");
        values.put(Contract.Cities.COUNTRY, "US");
        values.put(Contract.Cities.LAT, 40.714272);
        values.put(Contract.Cities.LONG, -74.005966);
        return values;
    }

    public static ContentValues getBarcelonaAsValues() {
        final ContentValues values = new ContentValues(5);
        values.put(Contract.Cities.ID, 3128760);
        values.put(Contract.Cities.CITY, "Barcelona");
        values.put(Contract.Cities.COUNTRY, "ES");
        values.put(Contract.Cities.LAT, 41.38879);
        values.put(Contract.Cities.LONG, 2.15899);
        return values;
    }
}
