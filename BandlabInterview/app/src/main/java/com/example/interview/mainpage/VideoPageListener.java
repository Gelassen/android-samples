package com.example.interview.mainpage;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.interview.App;
import com.example.interview.entity.Events;
import com.example.interview.videopage.VideoPageFragment;

import java.lang.ref.WeakReference;

/**
 * The intent of this class is encapsulate biz logic which happens on each page swipe
 *
 * Created by John on 10/4/2016.
 */
public class VideoPageListener extends ViewPager.SimpleOnPageChangeListener {

    private WeakReference<Context> contextWeakRef;

    public VideoPageListener(Context context) {
        // for current use case it is ok to store simply Context, but it would be good
        // to wrap it in weak reference to prevent memory leak for possible others use cases
        contextWeakRef = new WeakReference<>(context);
    }

    @Override
    public void onPageSelected(int position) {
        // notify listeners regarding this event
        Context context = contextWeakRef.get();
        if (context == null) return;


        Log.d(App.TAG, "onPageSelected: " + position);

        Events events = new Events();
        events.broadcast(context, events.getIntent(position));
    }
}
