package com.example.interview.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.ResultReceiver;
import android.util.Log;

import com.example.interview.App;
import com.example.interview.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
* The intent of this class is provide basic logic for http calls which would be customized
* in child classes. It would be good to have here network library like Retofit here, but for
 * current requirements it is overhead. OkHttp client included here as http client and urlconnection
 * have its own issue.
* */
public abstract class HttpCommand extends Command {

    protected Status status = new Status();

    protected Request request;

    @Override
    public void execute(Context context, ResultReceiver receiver) {
        super.execute(context, receiver);
        Log.d(App.TAG, String.format("%s is started", getClass().getSimpleName()));
        final long stime = System.currentTimeMillis();
        OkHttpClient client = new OkHttpClient();
        client = client.newBuilder()
                .connectTimeout(context.getResources().getInteger(R.integer.api_timeout), TimeUnit.SECONDS)
                .writeTimeout(context.getResources().getInteger(R.integer.api_timeout), TimeUnit.SECONDS)
                .readTimeout(context.getResources().getInteger(R.integer.api_timeout), TimeUnit.SECONDS)
                .build();

        onPreExecute(context);

        Response response = null;
        if (isNetworkAvailable()) {
            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    onProcessResponse(response);
                } else {
                    onProcessError(response.code());
                }
            } catch (IOException e) {
                Log.e(App.TAG, "Failed to execute request", e);
                onProcessError(Status.FAILED_TO_EXECUTE_REQUEST);
            }
        } else {
            onProcessError(Status.FAILED_NETWORK);
        }

        Log.d(App.TAG, String.format("%s is ended, %s",
                getClass().getSimpleName(),
                System.currentTimeMillis() - stime));
        notifyListeners(status);
    }

    protected abstract void onPreExecute(Context context);

    protected void onProcessError(final int statusCode) {
        status.add(statusCode);
    };

    protected abstract void onProcessResponse(Response response);

    private boolean isNetworkAvailable() {
        NetworkInfo networkInfo = ((ConnectivityManager) context
                .getSystemService(NetworkService.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        return networkInfo == null ? false : networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
