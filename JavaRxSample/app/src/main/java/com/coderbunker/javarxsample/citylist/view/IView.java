package com.coderbunker.javarxsample.citylist.view;


import com.coderbunker.javarxsample.dto.CityItem;

import java.util.List;

public interface IView {
    void showList(List<CityItem> model);
    void showEmptyList();
    void showError();
}
