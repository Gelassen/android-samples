package com.example.sensorssample;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

/**
 * Created by Gelassen on 03.08.2015.
 */
public class ContextListener implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int UPDATE_INTERVAL = 3000;

    private GoogleApiClient googleApiClient;
    private PendingIntent pendingIntent;

    public ContextListener(Context context, PendingIntent pendingIntent) {
        this.googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        this.pendingIntent = pendingIntent;
    }

    public void startListen() {
        if (googleApiClient.isConnected()) {
            ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(googleApiClient, UPDATE_INTERVAL, pendingIntent);
        } else {
            googleApiClient.connect();
        }
    }

    public void stopListen() {
        ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(googleApiClient, pendingIntent);
        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        startListen();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(App.TAG, "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(App.TAG, "onConnectionFailed");
    }
}
