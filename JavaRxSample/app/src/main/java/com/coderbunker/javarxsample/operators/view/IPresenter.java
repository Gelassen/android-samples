package com.coderbunker.javarxsample.operators.view;


import com.coderbunker.javarxsample.dto.CityItem;

import java.util.List;

public interface IPresenter {
    void showResult(List<CityItem> result);
    void onDestroy();
    void runFilter();
    void runMap();
    void takeNextItems(int skipAmount, int takeAmount);
    void runConcat();
    void runMerge();
    void runZip();
}
