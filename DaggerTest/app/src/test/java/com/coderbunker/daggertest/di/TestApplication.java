package com.coderbunker.daggertest.di;


import com.coderbunker.daggertest.AppApplication;

public class TestApplication extends AppApplication {

    @Override
    public AppComponent buildAppComponent() {
        return DaggerTestComponent.builder().build();
    }
}