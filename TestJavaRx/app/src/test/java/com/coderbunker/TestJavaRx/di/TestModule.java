package com.coderbunker.TestJavaRx.di;


import com.coderbunker.TestJavaRx.model.IModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class TestModule {

    @Singleton
    @Provides
    public IModel getModule() {
        return mock(IModel.class);
    }
}
