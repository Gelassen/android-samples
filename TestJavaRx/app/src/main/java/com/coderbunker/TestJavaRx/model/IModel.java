package com.coderbunker.TestJavaRx.model;


import java.util.List;

import rx.Observable;

public interface IModel {
    Observable<List<String>> getItems();
}
