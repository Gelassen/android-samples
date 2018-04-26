package com.home.vkphotos.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.ResultReceiver;
import android.util.Log;

import com.home.vkphotos.App;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class HttpCommand extends Command {

    private static final OkHttpClient client = new OkHttpClient();

    protected Status status = new Status();

    protected Request request;

    @Override
    public void execute(Context context, ResultReceiver receiver) {
        super.execute(context, receiver);
        Log.d(App.TAG, String.format("%s is started", getClass().getSimpleName()));
        final long stime = System.currentTimeMillis();
        Response response = null;
        try {
            createRequest(context);
            if (isNetworkAvailable()) {
                response = client.newCall(request).execute();
                final int code = response.code();
                //response.getStatusLine().getStatusCode();
                if (code == 200) {
                    processRequest(response);
                } else {
                    processError(code);
                }
            } else {
                processError(Status.FAILED_NETWORK);
            }
        } catch (IOException e) {
            Log.d(App.TAG, "Failed to execute command", e);
            processError(Status.FAILED_TO_EXECUTE_REQUEST);
        }
        Log.d(App.TAG, String.format("%s is ended, %s",
                getClass().getSimpleName(),
                System.currentTimeMillis() - stime));
        notifyListeners(status);
    }

    protected void processError(final int statusCode) {
        status.add(statusCode);
    }

    protected abstract void createRequest(Context context);

    protected void processRequest(Response response) {
        status.add(200);
    };

    private boolean isNetworkAvailable() {
        NetworkInfo networkInfo = ((ConnectivityManager) context
                .getSystemService(NetworkService.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        return networkInfo != null && (networkInfo.isAvailable() && networkInfo.isConnected());
    }
}
