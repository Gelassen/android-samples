package com.coderbunker.daggertest.dto;


import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class TestStub implements IModel {
    @Override
    public Observable<List<TestData>> getData() {
        return Observable.just(new ArrayList<>());
    }

    @Override
    public void generateModel() {
        // TODO
    }
}
