package com.home.xing.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.os.ResultReceiver;
import android.util.Log;

import com.home.xing.Xing;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;

/**
 * Created by Gleichmut on 9/4/2014.
 */
public abstract class HttpCommand extends Command {

    private static final AndroidHttpClient client = AndroidHttpClient.newInstance(Xing.TAG);

    protected Status status = new Status();
    protected HttpUriRequest httpUriRequest;

    @Override
    public void execute(Context context, ResultReceiver receiver) {
        super.execute(context, receiver);
        Log.d(Xing.TAG, String.format("%s is started", HttpCommand.class.getSimpleName()));
        final long stime = System.currentTimeMillis();
        HttpResponse response = null;
        try {
            createRequest();
            if (isNetworkAvailable()) {
                response = client.execute(httpUriRequest);
                final int code = response.getStatusLine().getStatusCode();
                if (2 == (code/100)) { // accept all 2xx codes
                    processRequest(response);
                } else {
                    processError(code, response);
                }
            } else {
                processError(Status.FAILED_NETWORK);
            }
        } catch (IOException e) {
            Log.d(Xing.TAG, "Failed to execute command", e);
            processError(Status.FAILED_TO_EXECUTE_REQUEST);
        } finally {
            if (response != null) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    try {
                        entity.consumeContent();
                    } catch (IOException e) {
                        Log.e(Xing.TAG, "Failed to consume http entity content", e);
                    }
                }
            }
        }
        Log.d(Xing.TAG, String.format("%s is ended, %s",
                HttpCommand.class.getSimpleName(),
                System.currentTimeMillis() - stime));
        notifyListeners(status);
    }

    private void processError(int code, HttpResponse response) {
        Header[] headers = response.getAllHeaders();
        for (Header header: headers) {
            Log.d(Xing.TAG, String.format(
                    "%s: %s ", header.getName(), header.getValue()));
        }
    }

    protected void processError(final int statusCode) {
        status.add(statusCode);
    };

    protected abstract void createRequest();
    protected abstract void processRequest(HttpResponse response);

    private boolean isNetworkAvailable() {
        NetworkInfo networkInfo = ((ConnectivityManager) context
                .getSystemService(NetworkService.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        return networkInfo == null ? false : networkInfo.isAvailable() && networkInfo.isConnected();
    }

    protected static class Headers {
        public static final String ACCEPT = "Accept";
        public static final String ACCEPT_DEFAULT_VALUE = "application/vnd.github.v3+json";

    }
}
