package com.example.dkazakov.weather.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Gleichmut on 9/13/2014.
 */
public class PreferenceHelper {

    private static final String NAMESPACE = PreferenceHelper.class.getName();

    private final static String KEY_WIDGET = NAMESPACE.concat(".KEY_WIDGET");
    private final static String KEY_SELECTED_CITY = NAMESPACE.concat(".KEY_SELECTED_CITY");
    private final static String KEY_SELECTED_CITY_ID = NAMESPACE.concat(".KEY_SELECTED_CITY_ID");
    private final static String KEY_SELECTED_ITEM = NAMESPACE.concat(".KEY_SELECTED_ITEM");

    private final static String KEY_CITY_WIDGET_ID_FORMAT = "%s_%s";

    public static String getCityIdByWidgetId(Context context, final int id) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(getKeyId(id), "");
    }

    public static void setCityIdByWidgetId(Context context, final int widgetId, final long cityId) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref
                .edit()
                .putString(getKeyId(widgetId), Long.toString(cityId))
                .commit();
    }

    public static void removeCityIdByWidgetId(Context context, final int widgetId) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref
                .edit()
                .remove(getKeyId(widgetId))
                .commit();
    }

    public static long getSelectedCityId(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getLong(KEY_SELECTED_CITY_ID, 0);
    }

    public static void setSelectedCityId(Context context, final long cityId) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref
                .edit()
                .putLong(KEY_SELECTED_CITY_ID, cityId)
                .commit();
    }

    public static String getSelectedCity(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(KEY_SELECTED_CITY, "");
    }

    public static void setSelectedCity(Context context, String city) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref
                .edit()
                .putString(KEY_SELECTED_CITY, city)
                .commit();
    }

    public static int getSelectedItem(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getInt(KEY_SELECTED_ITEM, 0);
    }

    public static void setSelectedItem(Context context, final int selection) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref
                .edit()
                .putInt(KEY_SELECTED_ITEM, selection)
                .commit();
    }

    private static String getKeyId(final int id) {
        return String.format(KEY_CITY_WIDGET_ID_FORMAT, KEY_WIDGET, id);
    }
}
