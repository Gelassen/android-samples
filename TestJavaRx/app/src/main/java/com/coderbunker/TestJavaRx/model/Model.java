package com.coderbunker.TestJavaRx.model;


import com.coderbunker.TestJavaRx.stub.Stub;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Model implements IModel {
    private Scheduler schedulerAsync;
    private Scheduler schedulerMainThread;

    public Model() {
        schedulerAsync = Schedulers.io();
        schedulerMainThread = AndroidSchedulers.mainThread();
    }

    @Override
    public Observable<List<String>> getItems() {
        return Observable.from(Stub.getData())
                .toList()
                .subscribeOn(schedulerAsync)
                .observeOn(schedulerMainThread);
    }
}
