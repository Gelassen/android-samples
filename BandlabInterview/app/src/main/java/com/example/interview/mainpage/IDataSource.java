package com.example.interview.mainpage;

/**
 * Common interface for data source used mostly by adapters
 *
 * Created by John on 10/3/2016.
 */
public interface IDataSource<T, E> {
    void update(T model);
    int getCount();
    E getItemForPosition(int position);
}
