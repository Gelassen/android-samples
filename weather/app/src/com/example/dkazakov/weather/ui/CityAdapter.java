package com.example.dkazakov.weather.ui;


import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.dkazakov.weather.storage.Contract;

public class CityAdapter extends ResourceCursorAdapter {

    private static final int NO_FLAG = 0;

    private static final String cityFormat = "%s, %s";

    private int cityIdIdx;
    private int cityIdx;
    private int countryIdx;
    private int latIdx;
    private int lonIdx;

    public CityAdapter(Context context, int layout, Cursor c) {
        super(context, layout, c, NO_FLAG);
    }

    @Override
    public Cursor swapCursor(Cursor newCursor) {
        initIdx(newCursor);
        return super.swapCursor(newCursor);
    }

    private void initIdx(Cursor cursor){
        if (cursor != null) {
            cityIdIdx = cursor.getColumnIndex(Contract.Cities.ID);
            cityIdx = cursor.getColumnIndex(Contract.Cities.CITY);
            countryIdx = cursor.getColumnIndex(Contract.Cities.COUNTRY);
            latIdx = cursor.getColumnIndex(Contract.Cities.LAT);
            lonIdx = cursor.getColumnIndex(Contract.Cities.LONG);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = super.newView(context, cursor, parent);
        ViewHolder holder = new ViewHolder(view.findViewById(android.R.id.text1));
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(
                String.format(cityFormat, cursor.getString(cityIdx), cursor.getString(countryIdx))
        );
        holder.lat = cursor.getDouble(latIdx);
        holder.lon = cursor.getDouble(lonIdx);
        holder.id = cursor.getLong(cityIdIdx);
    }

    public class ViewHolder {
        private final TextView text;
        private long id;
        private double lat;
        private double lon;

        public ViewHolder(View view) {
            this.text = (TextView) view;
        }

        public long getId() {
            return id;
        }

        public double getLat() {
            return lat;
        }

        public double getLon() {
            return lon;
        }

        public String getCity() {
            return text.getText().toString().split(",")[0];
        }

        public String getCountry() {
            return text.getText().toString().split(",")[1];
        }
    }
}
