package com.home.vkphotos.login;


import android.text.TextUtils;
import android.util.Patterns;

import com.vk.sdk.VKAccessToken;

public class LoginPresenter implements ILoginPresenter {

    private ILoginView view;
    private AuthModule authModule;

    public LoginPresenter(ILoginView view, AuthModule authModule) {
        this.view = view;
        this.authModule = authModule;
    }

    @Override
    public void onLogin(String email, String password) {
        // TODO vclidate email
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(password)) {
            authModule.auth();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.showEmailError();
        } else if (TextUtils.isEmpty(password)) {
            view.showPwdError();
        }
    }

    @Override
    public void onStart() {
        if (VKAccessToken.currentToken() != null) {
            view.launchMainScreen();
        }
    }
}
