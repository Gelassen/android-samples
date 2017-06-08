package com.coderbunker.daggertest.di;


import com.coderbunker.daggertest.dto.IModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestModule {

    @Provides
    @Singleton
    public IModel getModel() {
        return new StubModel();
    }

    private static class StubModel implements IModel {
        @Override
        public void generateModel() {
            // no op
        }
    }
}
