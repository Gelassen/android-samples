package com.example.interview;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.example.interview.network.Status;

/**
 * Created by John on 9/11/2016.
 */
public class AppResultReceiver extends ResultReceiver {

    public interface Callbacks {
        void onSuccess();
        void onFailed();
        void onNetworkIsNotAvailable();
    }

    private Callbacks listener;

    public AppResultReceiver(Handler handler) {
        super(handler);
    }

    public void setListener(Callbacks callbacks) {
        this.listener = callbacks;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if (listener == null) return;

        Status status = Status.from(resultData);
        if (status.isSuccess()) {
            listener.onSuccess();
        } else {
            final int code = status.getStatusCode();
            switch (code) {
                case Status.FAILED_NETWORK:
                    listener.onNetworkIsNotAvailable();
                    break;
                default:
                    listener.onFailed();
            }
        }
    }
}
