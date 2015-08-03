package com.example.sensorssample;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

public class RecognitionActivity extends Activity {

    public static final int ACTIVITY_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(getIntent());
        List<DetectedActivity> activities = result.getProbableActivities();
        DetectedActivity activity = result.getMostProbableActivity();

        TextView textView = (TextView) findViewById(R.id.testView);
        textView.setText(String.valueOf(activity.getType()));
    }

}
