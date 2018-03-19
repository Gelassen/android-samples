package com.example.interview.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * The intent of this class is encapsulate work with shared preferences
 *
 * Created by John on 10/3/2016.
 */
public class Storage {

    private static final String KEY_VIDEO_AFTER = "KEY_VIDEO_AFTER";

    public void saveNextPageInfo(Context context, String data) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(KEY_VIDEO_AFTER, data).apply();
    }
}
