package com.coderbunker.daggertest.presenter;


import android.util.Log;

import com.coderbunker.daggertest.App;
import com.coderbunker.daggertest.AppApplication;
import com.coderbunker.daggertest.dto.IModel;

import javax.inject.Inject;

public class FakePresenter {

    @Inject
    IModel model;

    public FakePresenter() {
        AppApplication.getAppComponent().inject(this);
    }

    public void doSomething() {
        Log.d(App.TAG, "doSomething");
        model.getData();
    }
}
