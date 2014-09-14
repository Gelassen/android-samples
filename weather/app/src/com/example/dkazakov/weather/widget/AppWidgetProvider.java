package com.example.dkazakov.weather.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.dkazakov.weather.R;
import com.example.dkazakov.weather.Weather;
import com.example.dkazakov.weather.network.commands.UpdateWidgetCommand;
import com.example.dkazakov.weather.storage.PreferenceHelper;
import com.example.dkazakov.weather.ui.MainActivity;

/**
 * Created by Gleichmut on 9/13/2014.
 */
public class AppWidgetProvider extends android.appwidget.AppWidgetProvider {

    private static final long UPDATE_PERIOD = 3 * 60 * 60 * 1000; // 3 hours
    private static final String KEY_UPDATE = "KEY_UPDATE";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Bundle extras = intent.getExtras();
        if (extras != null && extras.containsKey(KEY_UPDATE)) {
            updateWidgets(context);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(Weather.TAG, "Widget is updated, with ids size " + appWidgetIds.length);
        new UpdateWidgetCommand(appWidgetIds).start(context, null);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(Weather.TAG, "Widget is deleted, with ids size " + appWidgetIds.length);
        for (int appWidgetId : appWidgetIds) {
            PreferenceHelper.removeCityIdByWidgetId(context, appWidgetId);
        }
    }

    public static void updateWidgets(Context context){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, AppWidgetProvider.class)
        );
        Log.d(Weather.TAG, "Update widget, with ids size " + appWidgetIds.length);
        if(appWidgetIds == null || appWidgetIds.length == 0)
            return;
        new UpdateWidgetCommand(appWidgetIds).start(context, null);
    }

    public static void scheduleAlarms(Context context) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AppWidgetProvider.class);
        intent.putExtra(KEY_UPDATE, true);
        PendingIntent pending = PendingIntent.getBroadcast(context, 0, intent, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME, UPDATE_PERIOD, pending);
    }
}
