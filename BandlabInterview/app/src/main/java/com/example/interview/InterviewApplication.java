package com.example.interview;

import android.app.Application;

import com.example.interview.entity.NetworkLibrary;

/**
 * The intent of this class is to have runtime storage for some classes used
 * during all app lifecycle
 *
 * Created by John on 10/3/2016.
 */
public class InterviewApplication extends Application {

    private NetworkLibrary networkLibrary;

    @Override
    public void onCreate() {
        super.onCreate();

        // TODO add strict mode
        // TODO add canary leak

        networkLibrary = new NetworkLibrary();
        networkLibrary.init(this);
    }

    public NetworkLibrary getNetworkLibrary() {
        return networkLibrary;
    }
}
