package com.home.vkphotos.login;


import android.content.Context;

import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

public class AuthModule {
    private Context context;

    public AuthModule(Context context) {
        this.context = context;
    }

    public void auth() {
//        VKSdk.login(this, VKScope.PHOTOS);
    }
}
