package com.example.interview.entity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

/**
 * The intent of this class is encapsulate work with events broadcasting and handled by app
 *
 * Created by John on 10/4/2016.
 */
public class Events {
    public static final String EVENT_PAGE_CHANGED = "EVENT_PAGE_CHANGED";

    private static final String EXTRA_PAGE_POSITION = "EXTRA_PAGE_POSITION";

    public Intent getIntent(int pagePosition) {
        Intent intent = new Intent(EVENT_PAGE_CHANGED);
        intent.putExtra(EXTRA_PAGE_POSITION, pagePosition);
        return intent;
    }

    public int getPagePosition(Intent intent) {
        return intent.getIntExtra(EXTRA_PAGE_POSITION, 0);
    }

    public void broadcast(Context context, Intent intent) {
        LocalBroadcastManager.getInstance(context)
                .sendBroadcast(intent);
    }

    public void registerListener(Context context, BroadcastReceiver broadcastReceiver, final String action) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(action);
        LocalBroadcastManager.getInstance(context)
                .registerReceiver(broadcastReceiver, intentFilter);
    }

    public void unregisterListener(Context context, BroadcastReceiver broadcastReceiver) {
        LocalBroadcastManager.getInstance(context)
                .unregisterReceiver(broadcastReceiver);
    }
}
