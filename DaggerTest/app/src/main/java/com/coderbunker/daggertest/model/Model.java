package com.coderbunker.daggertest.model;


import com.coderbunker.daggertest.App;
import com.coderbunker.daggertest.Test;
import com.coderbunker.daggertest.dto.IModel;
import com.coderbunker.daggertest.dto.TestData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class Model implements IModel {


    // TODO add dagger dependency on the next stage
//    @Inject
//    @Named(App.Params.MAIN_THREAD)
    Scheduler schedulerMainThread;

//    @Inject
//    @Named(App.Params.ASYNC)
    Scheduler schedulerAsync;

    public Model() {
        schedulerMainThread = Schedulers.immediate();//AndroidSchedulers.mainThread();
        schedulerAsync = Schedulers.immediate();//Schedulers.io();
    }

    public Observable<List<TestData>> getData() {
        return Observable.from(Test.generateStubData())
                .toList()
                .subscribeOn(schedulerAsync)
                .observeOn(schedulerMainThread);
    }

    @Override
    public void generateModel() {
        // no op
    }
}
