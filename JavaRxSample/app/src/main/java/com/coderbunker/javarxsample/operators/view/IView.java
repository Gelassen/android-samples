package com.coderbunker.javarxsample.operators.view;


import com.coderbunker.javarxsample.dto.CityItem;

import java.util.List;

public interface IView {
    void showResult(List<CityItem> result);
    void showResult(String str);
    void showError();
}
