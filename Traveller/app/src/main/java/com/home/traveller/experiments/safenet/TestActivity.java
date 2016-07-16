package com.home.traveller.experiments.safenet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.*;
import com.google.android.gms.safetynet.SafetyNet;
import com.home.traveller.App;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by dmitry.kazakov on 1/23/2016.
 */
public class TestActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        ResultCallback<SafetyNetApi.AttestationResult> {

    private GoogleApiClient.ConnectionCallbacks connectionCallbacks;
    private GoogleApiClient googleApiClient;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectionCallbacks = this;
        connectGoogleApiClient();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void connectGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(connectionCallbacks)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(App.TAG, "Connected to google API");
        callAttestation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(App.TAG, "Connection suspended to google API");
    }


    protected void callAttestation() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                byte[] nounce = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
                SafetyNet.SafetyNetApi
                        .attest(googleApiClient, nounce)
                        .setResultCallback(TestActivity.this);
            }
        });
    }

    @Override
    public void onResult(SafetyNetApi.AttestationResult attestationResult) {
        Status status = attestationResult.getStatus();
        if (status.isSuccess()) {
            Log.d(App.TAG, "SafetyNet: attestation is completed");
            Log.d(App.TAG, "SafetyNet: " + attestationResult.getJwsResult());
        } else {
            Log.e(App.TAG, "SafetyNet: something goes wrong");
        }
    }
}
