package com.coderbunker.daggertest.dto;


import java.util.List;

import rx.Observable;

public interface IModel {
    Observable<List<TestData>> getData();
    void generateModel();
}
