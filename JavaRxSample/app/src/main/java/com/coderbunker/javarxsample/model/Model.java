package com.coderbunker.javarxsample.model;


import android.util.Log;

import com.coderbunker.javarxsample.App;
import com.coderbunker.javarxsample.dto.AirCompaniesItem;
import com.coderbunker.javarxsample.dto.CityItem;
import com.coderbunker.javarxsample.test.Test;
import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import rx.Observable;

public class Model {

    @RxLogObservable
    public Observable<List<CityItem>> getModel() {
        Log.d(App.TAG, "getModel: " + Thread.currentThread().getId());
        return Observable.just(Test.generateTestData());
    }

    @RxLogObservable
    public Observable<List<AirCompaniesItem>> getAirCompanies() {
        Log.d(App.TAG, "getAirCompanies: " + Thread.currentThread().getId());
        return Observable.just(Test.generateAirCompaniesData());
    }
}
