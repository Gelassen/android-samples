package com.coderbunker.TestJavaRx.di;

import com.coderbunker.TestJavaRx.JavaRxTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = TestModule.class)
public interface TestComponent extends AppComponent{
    void inject(JavaRxTest entity);
}
