package com.example.dkazakov.weather.network.commands;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.widget.RemoteViews;

import com.example.dkazakov.weather.R;
import com.example.dkazakov.weather.network.Command;
import com.example.dkazakov.weather.storage.Contract;
import com.example.dkazakov.weather.storage.PreferenceHelper;
import com.example.dkazakov.weather.ui.MainActivity;

/**
 * Created by Gleichmut on 9/13/2014.
 *
 * This command binds widgets with weather data. It mix View, Model, Controller in the one place
 * and it is ikey till we don't expect extend or modify this logic. If app will require do that
 * we must separate these levels
 *
 */
public class UpdateWidgetCommand extends Command {

    private static final int NO_REQUEST_CODE = 0;

    private final int[] ids;

    public UpdateWidgetCommand(int[] ids) {
        this.ids = ids;
    }

    @Override
    public void execute(Context context, ResultReceiver receiver) {
        super.execute(context, receiver);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        for (int appWidgetId : ids) {

            final String cityId = PreferenceHelper.getCityIdByWidgetId(context, appWidgetId);
            final String packageName = context.getPackageName();
            RemoteViews widget = bindData(cityId, packageName);

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pending = PendingIntent.getActivity(
                    context, NO_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT
            );

            widget.setOnClickPendingIntent(WeatherWidgetView.container, pending);
            manager.updateAppWidget(appWidgetId, widget);
        }
    }

    private RemoteViews bindData(final String cityId, final String packageName) {
        Cursor cursor = getContent(cityId);
        final int tempIdx = cursor.getColumnIndex(Contract.Weather.DAY_TEMP);
        final int descIdx = cursor.getColumnIndex(Contract.Weather.DESC);

        // show only current weather
        while (cursor.moveToFirst()) {
            RemoteViews widget = new RemoteViews(packageName, WeatherWidgetView.layout);
            widget.setTextViewText(WeatherWidgetView.city, getCity(cityId));
            widget.setTextViewText(WeatherWidgetView.temp, Integer.toString(cursor.getInt(tempIdx)));
            widget.setTextViewText(WeatherWidgetView.description, cursor.getString(descIdx));
            return widget;
        }
        return null;
    }

    private String getCity(final String cityId) {
        String selection = Contract.Cities.ID + "=?";
        String[] selectionArgs = new String[] { cityId };
        String[] projection = new String[] { Contract.Cities.CITY };
        Cursor cursor = context.getContentResolver().query(
                Contract.contentUri(Contract.Cities.class),
                projection,
                selection,
                selectionArgs,
                null
        );
        if (cursor.moveToFirst()) {
            final int cityIdx = cursor.getColumnIndex(Contract.Cities.CITY);
            return cursor.getString(cityIdx);
        }
        return "";
    }

    private Cursor getContent(final String cityId) {
        String selection = Contract.Weather.CITY_ID + "=?";
        String[] selectionArgs = new String[] { cityId };
        return context.getContentResolver().query(
                Contract.contentUri(Contract.Weather.class),
                null,
                selection,
                selectionArgs,
                Contract.Weather.DAY + " ASC"
        );
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ids.length);
        for (int id : ids) {
            dest.writeInt(id);
        }
    }

    public final static Parcelable.Creator<UpdateWidgetCommand> CREATOR = new Parcelable.Creator<UpdateWidgetCommand>() {

        @Override
        public UpdateWidgetCommand[] newArray(int size) {
            return new UpdateWidgetCommand[size];
        }

        @Override
        public UpdateWidgetCommand createFromParcel(Parcel source) {
            final int length = source.readInt();
            int[] ids = new int[length];
            for (int idx = 0; idx < length; idx++) {
                ids[idx] = source.readInt();
            }
            return new UpdateWidgetCommand(ids);
        }
    };

    private static class WeatherWidgetView {
        private static final int layout = R.layout.weather_widget;
        private static final int container = R.id.widget;
        private static final int city = R.id.city;
        private static final int temp = R.id.temperature;
        private static final int description = R.id.description;
    }
}
