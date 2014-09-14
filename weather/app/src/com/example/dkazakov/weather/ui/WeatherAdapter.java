package com.example.dkazakov.weather.ui;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.dkazakov.weather.R;
import com.example.dkazakov.weather.storage.Contract;


import java.util.Calendar;

public class WeatherAdapter extends ResourceCursorAdapter {

    private static final int NO_FLAG = 0;

    private int dateIdx;
    private int descIdx;
    private int dayTempIdx;
    private int maxTempIdx;
    private int minTempIdx;
    private int imageIdx;

    private final String[] days;

    public WeatherAdapter(Context context, int layout, Cursor c) {
        super(context, layout, c, NO_FLAG);
        days = context.getResources().getStringArray(R.array.days);
    }

    @Override
    public Cursor swapCursor(Cursor newCursor) {
        initIdx(newCursor);
        return super.swapCursor(newCursor);
    }

    private void initIdx(Cursor newCursor) {
        if (newCursor != null) {
            dateIdx = newCursor.getColumnIndex(Contract.Weather.DAY);
            descIdx = newCursor.getColumnIndex(Contract.Weather.DESC);
            dayTempIdx = newCursor.getColumnIndex(Contract.Weather.DAY_TEMP);
            maxTempIdx = newCursor.getColumnIndex(Contract.Weather.MAX_TEMP);
            minTempIdx = newCursor.getColumnIndex(Contract.Weather.MIN_TEMP);
            imageIdx = newCursor.getColumnIndex(Contract.Weather.ICON);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = super.newView(context, cursor, parent);
        ViewHolder holder = new ViewHolder(
                view.findViewById(R.id.weather_day),
                view.findViewById(R.id.weather_desc),
                view.findViewById(R.id.weather_temp_day),
                view.findViewById(R.id.weather_temp_max),
                view.findViewById(R.id.weather_temp_min)
        );
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.desc.setText(cursor.getString(descIdx));
        holder.dayTemp.setText(Integer.toString(cursor.getInt(dayTempIdx)));
        holder.dayMinTemp.setText(Integer.toString(cursor.getInt(minTempIdx)));
        holder.dayMaxTemp.setText(Integer.toString(cursor.getInt(maxTempIdx)));

        final long date = cursor.getLong(dateIdx) * 1000;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        final int shiftForArray = 1;
        final int dayIdx = calendar.get(Calendar.DAY_OF_WEEK) - shiftForArray;
        holder.day.setText(days[dayIdx]);
    }

    private class ViewHolder {
        private TextView day;
        private TextView desc;
        private TextView dayTemp;
        private TextView dayMaxTemp;
        private TextView dayMinTemp;

        public ViewHolder(View day, View desc, View dayTemp, View dayMaxTemp, View dayMinTemp) {
            this.day = (TextView) day;
            this.desc = (TextView) desc;
            this.dayTemp = (TextView) dayTemp;
            this.dayMaxTemp = (TextView) dayMaxTemp;
            this.dayMinTemp = (TextView) dayMinTemp;
        }
    }
}
