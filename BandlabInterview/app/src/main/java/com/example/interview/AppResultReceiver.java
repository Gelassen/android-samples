package com.example.interview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.example.interview.network.Status;

/**
 * The intent of this class is receive data from separate threads and forward it to the listener.
 *
 * Created by John on 10/4/2016.
 */
@SuppressLint("ParcelCreator")
public class AppResultReceiver extends ResultReceiver {

    public AppResultReceiver(Handler handler) {
        super(handler);
    }

    private Callbacks listener;

    public void setListener(Callbacks listener) {
        this.listener = listener;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);

        Status status = Status.from(resultData);
        if (status.isSuccess()) {
            if (listener != null) listener.onSuccess(status);
        } else {
            if (listener != null) listener.onError(status);
        }
    }

    public boolean isNotListen() {
        return listener == null;
    }

    public interface Callbacks {
        void onSuccess(Status status);
        void onError(Status status);
    }
}
