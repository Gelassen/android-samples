package com.home.vkphotos;


import android.app.Application;

import com.vk.sdk.VKSdk;

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
    }
}
