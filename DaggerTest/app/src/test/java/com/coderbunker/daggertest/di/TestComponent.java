package com.coderbunker.daggertest.di;


import com.coderbunker.daggertest.BaseTest;
import com.coderbunker.daggertest.ListTest;
import com.coderbunker.daggertest.integration.ModifiedTest;
import com.coderbunker.daggertest.integration.OriginalTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestModule.class})
public interface TestComponent extends AppComponent {
    void inject(BaseTest entity);
    void inject(OriginalTest entity);
    void inject(ModifiedTest entity);
    void inject(ListTest entity);
}
