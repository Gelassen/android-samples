package com.coderbunker.daggertest;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.coderbunker.daggertest.di.DaggerViewComponent;
import com.coderbunker.daggertest.presenter.FakePresenter;

import javax.inject.Inject;

public class SampleActivity extends Activity {

    @Inject
    FakePresenter fakePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerViewComponent.builder()
                .build()
                .inject(this);

        fakePresenter.doSomething();
    }
}
