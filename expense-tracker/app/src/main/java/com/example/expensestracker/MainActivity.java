package com.example.expensestracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.expensestracker.model.DriveFiles;
import com.example.expensestracker.service.NetworkService;
import com.example.expensestracker.util.SimpleObserver;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    private GoogleAccountCredential credentials;
    private NetworkService service;
    private Drive driveService;

    private String[] scopes = { DriveScopes.DRIVE_METADATA_READONLY };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 854456447771-o2oc4uctck7ujludcphn2cmr0i8c5vq1.apps.googleusercontent.com

//        service = new NetworkService((AppApplication) getApplication());
//        service.getFiles().subscribe(getDriveFilesObserverable());
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    process();
//                } catch (Exception e) {
//                    Log.e(TAG, "Error ro process drive response", e);
//                }
//            }
//        });
//        thread.start();

        credentials = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(scopes))
                .setBackOff(new ExponentialBackOff());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO process data
                //
                HttpTransport transport = AndroidHttp.newCompatibleTransport();
                JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
                driveService = new com.google.api.services.drive.Drive.Builder(
                        transport, jsonFactory, credentials)
                        .setApplicationName("Drive API Android Quickstart")
                        .build();

                Drive.Files files = driveService.files();
                try {
                    files.list();
                } catch (IOException e) {
                    Log.e(TAG, "Failed to obtain files", e);
                }

            }
        });
        thread.start();

    }

    private SimpleObserver<DriveFiles> getDriveFilesObserverable() {
        return new SimpleObserver<DriveFiles>() {
            @Override
            public void onNext(DriveFiles data) {
                super.onNext(data);
                Log.e(TAG, "Data items count" + data.getItems().size());
                Log.e(TAG, "Data: " + data.getSelfLink());
            }

            @Override
            public void onError(Throwable error) {
                super.onError(error);
                Log.e(TAG, "Failed data");
            }
        };
    }

    private final OkHttpClient client = new OkHttpClient();

    public void process() throws Exception {
        Request request = new Request.Builder()
                .url("https://www.googleapis.com/drive/v2/files")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }

            System.out.println(response.body().string());
        }
    }
}
