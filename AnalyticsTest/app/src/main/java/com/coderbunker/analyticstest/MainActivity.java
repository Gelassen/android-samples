package com.coderbunker.analyticstest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class MainActivity extends AppCompatActivity {

    private Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppApplication appApplication = (AppApplication) getApplication();
        tracker = appApplication.getDefaultTracker();

        GoogleAnalytics.getInstance(this).setLocalDispatchPeriod(10);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppApplication appApplication = (AppApplication) getApplication();
                Tracker tracker = appApplication.getDefaultTracker();
                tracker.setScreenName("Main activity");
                tracker.send(new HitBuilders.ScreenViewBuilder().build());
                GoogleAnalytics.getInstance(MainActivity.this).dispatchLocalHits();

//                GoogleAnalytics.getInstance(MainActivity.this).

//                tracker.send(new HitBuilders.EventBuilder()
//                        .setCategory("Action")
//                        .setAction("Share")
//                        .build());
//
//                GoogleAnalytics.getInstance(MainActivity.this).reportActivityStart(MainActivity.this);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        GoogleAnalytics.getInstance(MainActivity.this).reportActivityStop(MainActivity.this);
//                        GoogleAnalytics.getInstance(MainActivity.this).dispatchLocalHits();
//                    }
//                }).start();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }
}
