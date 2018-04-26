package com.home.vkphotos.login;


public interface ILoginPresenter {
    void onLogin(String email, String password);

    void onStart();
}
