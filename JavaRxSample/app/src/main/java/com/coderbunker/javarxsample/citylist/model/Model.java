package com.coderbunker.javarxsample.citylist.model;


import com.coderbunker.javarxsample.citylist.dto.CityItem;
import com.coderbunker.javarxsample.test.Test;
import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import rx.Observable;

public class Model {

    @RxLogObservable
    public Observable<List<CityItem>> getModel() {
        return Observable.just(Test.generateTestData());
    }
}
