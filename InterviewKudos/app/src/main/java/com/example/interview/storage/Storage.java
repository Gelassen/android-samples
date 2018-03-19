package com.example.interview.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * The intent of this class is encapsulate work with shared preferences.
 *
 * Created by John on 9/11/2016.
 */
public class Storage {
    private static final String EXTA_FIRST_RUN = "EXTA_FIRST_RUN";

    public static boolean isFirstRun(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(EXTA_FIRST_RUN, true);
    }

    public static void setFirstRun(Context context) {
        SharedPreferences preferenes = PreferenceManager.getDefaultSharedPreferences(context);
        preferenes.edit().putBoolean(EXTA_FIRST_RUN, false).apply();
    }
}
