package com.home.xing.network;

import android.content.Context;
import android.os.Parcelable;
import android.os.ResultReceiver;

/**
 * Created by Gleichmut on 9/4/2014.
 */
public abstract class Command implements Parcelable {

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
    }

    protected void notifyListeners(final Status result) {
        if (notify && resultReceiver != null) {
            resultReceiver.send(0, result.getBundle());
        }
    }
}
