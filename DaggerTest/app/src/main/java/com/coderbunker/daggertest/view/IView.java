package com.coderbunker.daggertest.view;


import com.coderbunker.daggertest.dto.TestData;

import java.util.List;

public interface IView {
    void showMessage(String msg);

    void showList();

    void showList(List<TestData> data);

    void showEmptyList();
}
