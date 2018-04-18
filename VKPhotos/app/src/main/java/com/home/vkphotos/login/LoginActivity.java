package com.home.vkphotos.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.home.vkphotos.App;
import com.home.vkphotos.BaseActivity;
import com.home.vkphotos.photos.preview.FilmsActivity;
import com.home.vkphotos.R;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class LoginActivity extends BaseActivity implements VKCallback<VKAccessToken> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        VKSdk.initialize(this);
        if (VKAccessToken.currentToken() == null) {
            FilmsActivity.start(this);
        } else {
            VKSdk.login(this, VKScope.PHOTOS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CANCELED) return;

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, this)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            // TODO show snackbar
            Toast.makeText(this, "Error to obtain the data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResult(VKAccessToken res) {
        Log.d(App.TAG, "onResult: Token: " + res);
        res.saveTokenToSharedPreferences(this, App.Constants.TOKEN);
        FilmsActivity.start(this);
    }

    @Override
    public void onError(VKError error) {
        Log.e(App.TAG, "onError: error: " + error);
    }
}
