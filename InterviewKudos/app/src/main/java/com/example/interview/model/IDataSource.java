package com.example.interview.model;

/**
 * The intent of this class is provide common set of methods which are used by RecycletView adapters
 * and hide details of implementation in child classes
 *
 * Created by John on 9/11/2016.
 */
public interface IDataSource<T, E> {
    void update(T data);
    int getItemCount();
    E getItemForPosition(int index);
}
