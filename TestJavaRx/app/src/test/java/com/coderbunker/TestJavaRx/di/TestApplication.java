package com.coderbunker.TestJavaRx.di;


import com.coderbunker.TestJavaRx.App;
import com.coderbunker.TestJavaRx.di.AppComponent;

public class TestApplication extends App {

    @Override
    protected AppComponent buildComponent() {
        return DaggerTestComponent.builder().build();
    }
}
