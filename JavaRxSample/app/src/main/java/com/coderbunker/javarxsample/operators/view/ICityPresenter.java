package com.coderbunker.javarxsample.operators.view;


import com.coderbunker.javarxsample.dto.CityItem;

import java.util.List;

public interface ICityPresenter extends IPresenter{
    void showResult(List<CityItem> result);
    void runFilter();
    void runMap();
    void takeNextItems(int skipAmount, int takeAmount);
    void runConcat();
    void runMerge();
    void runZip();
    void runDataFromCache();
    void runDataWithException();
    void runWithExceptionPropagate();
    void runWithExceptionObservable();
    void runConnectedObservable();
    void runChainWithPartsInUIThread();
}
