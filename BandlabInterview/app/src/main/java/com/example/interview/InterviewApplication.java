package com.example.interview;

import android.app.Application;
import android.os.StrictMode;

import com.example.interview.entity.NetworkLibrary;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * The intent of this class is to have runtime storage for some classes used
 * during all app lifecycle
 *
 * Created by John on 10/3/2016.
 */
public class InterviewApplication extends Application {

    private NetworkLibrary networkLibrary;
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        // add strict mode
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyDeath()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }

        // add canary leak
        if (BuildConfig.DEBUG) {
            refWatcher = LeakCanary.install(this);
        }

        networkLibrary = new NetworkLibrary();
        networkLibrary.init(this);
    }

    public NetworkLibrary getNetworkLibrary() {
        return networkLibrary;
    }

    public void watch(Object object) {
        if (BuildConfig.DEBUG) {
            refWatcher.watch(object);
        }
    }
}
