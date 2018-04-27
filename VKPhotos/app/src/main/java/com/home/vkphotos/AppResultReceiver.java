package com.home.vkphotos;


import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.home.vkphotos.network.Status;

public class AppResultReceiver extends ResultReceiver {

    public interface Listener {
        void onSuccess(Status status);
        void onError();
    }

    private Listener listener;

    public AppResultReceiver(Handler handler) {
        super(handler);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if (listener == null) return;

        Status status = Status.from(resultData);
        if (status.isSuccess()) {
            listener.onSuccess(status);
        }
    }
}
