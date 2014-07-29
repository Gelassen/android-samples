package com.dataart.school.webservice;

import java.io.IOException;
import java.io.Serializable;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.util.Log;

import com.dataart.school.Schema;

public abstract class HttpCommand implements Serializable {

    protected Context context;
    
    private static final AndroidHttpClient client = AndroidHttpClient.newInstance(Schema.TAG);

    public void setContext(Context context) {
        this.context = context;
    }

    public void execute() {
        final long start = System.currentTimeMillis();
        Log.d(Schema.TAG, String.format("%s is started", getClass().getName()));
        try {
            HttpUriRequest request = getRequest();
            HttpResponse response = client.execute(request);
            final int code = response.getStatusLine().getStatusCode();
            Log.e(Schema.TAG, "Server returns " + code);
            if (code == HttpStatus.SC_OK) {
                String json = EntityUtils.toString(response.getEntity());
                handleResult(json);
            }
        } catch (IOException e) {
            Log.e(Schema.TAG, "Failed to execute response", e);
        }
        final long end = System.currentTimeMillis() - start;
        Log.e(Schema.TAG, String.format("Command ends. It takes %s millis", end));
    }
    
    protected abstract HttpUriRequest getRequest();
    
    protected abstract void handleResult(String json);
    
}
