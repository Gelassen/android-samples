package com.example.interview.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.os.ResultReceiver;

import com.example.interview.InterviewApplication;
import com.example.interview.entity.NetworkLibrary;

public abstract class Command implements Parcelable{

    protected ResultReceiver resultReceiver;
    protected Context context;
    protected boolean notify = true;

    public final void start(Context context, ResultReceiver resultReceiver) {
        NetworkService.start(context, this, resultReceiver);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void execute(Context context, ResultReceiver receiver) {
        this.context = context;
        this.resultReceiver = receiver;

        ((InterviewApplication) context.getApplicationContext()).initNetworkLibrary(context);
    }

    protected void notifyListeners(final Status result) {
        if (notify && resultReceiver != null) {
            resultReceiver.send(0, result.getBundle());
        }
    }

    protected boolean isNetworkAvailable() {
        NetworkInfo networkInfo = ((ConnectivityManager) context
                .getSystemService(NetworkService.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        return networkInfo == null ? false : networkInfo.isAvailable() && networkInfo.isConnected();
    }

}
