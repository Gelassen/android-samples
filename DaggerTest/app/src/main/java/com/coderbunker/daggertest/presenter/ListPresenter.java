package com.coderbunker.daggertest.presenter;


import com.coderbunker.daggertest.view.IView;

public class ListPresenter {
    private IView view;

    public ListPresenter(IView view) {
        this.view = view;
    }


    public void showList() {
        view.showList();
    }
}
