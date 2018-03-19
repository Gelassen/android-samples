package com.coderbunker.daggertest.di;

import com.coderbunker.daggertest.dto.IModel;
import com.coderbunker.daggertest.dto.TestStub;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    public static IModel getModel() {
        return new TestStub();
    }
}
