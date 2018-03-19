package com.coderbunker.appdagger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.coderbunker.appdagger.presenters.IPresenter;
import com.coderbunker.appdagger.views.IView;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements IView {

    @Inject
    IPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppApplication application = (AppApplication) getApplication();
        application.getAppComponent().inject(this);

        String str = "hello world!";
    }

    @Override
    public void onShow(String message) {
        Log.d(App.TAG, "onShow: " + message);
    }

    @Override
    public void onError(String message) {
        Log.d(App.TAG, "onError: " + message);
    }
}
