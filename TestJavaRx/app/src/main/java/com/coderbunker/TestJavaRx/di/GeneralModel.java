package com.coderbunker.TestJavaRx.di;

import android.content.Context;

import com.coderbunker.TestJavaRx.model.IModel;
import com.coderbunker.TestJavaRx.model.Model;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class GeneralModel {

    private Context context;

    public GeneralModel(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Context getContext() {
        return context;
    }

    @Singleton
    @Provides
    public IModel getModel() {
        return new Model();
    }
}
