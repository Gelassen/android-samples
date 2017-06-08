package com.coderbunker.daggertest.di;


import com.coderbunker.daggertest.BaseTest;
import com.coderbunker.daggertest.ListTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestModule.class})
public interface TestComponent extends AppComponent {
    void inject(BaseTest entity);
    void inject(ListTest entity);
}
