package com.coderbunker.TestJavaRx.di;

import com.coderbunker.TestJavaRx.model.IModel;
import com.coderbunker.TestJavaRx.model.Model;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class GeneralModel {

    @Singleton
    @Provides
    public IModel getModel() {
        return new Model();
    }
}
