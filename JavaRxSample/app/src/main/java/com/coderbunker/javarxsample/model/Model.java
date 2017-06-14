package com.coderbunker.javarxsample.model;


import com.coderbunker.javarxsample.dto.AirCompaniesItem;
import com.coderbunker.javarxsample.dto.CityItem;
import com.coderbunker.javarxsample.test.Test;
import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import rx.Observable;

public class Model {

    @RxLogObservable
    public Observable<List<CityItem>> getModel() {
        return Observable.just(Test.generateTestData());
    }

    @RxLogObservable
    public Observable<List<AirCompaniesItem>> getAirCompanies() {
        return Observable.just(Test.generateAirCompaniesData());
    }
}
