package com.example.interview.convertors;

/**
 * The intent of this interface is encapsulate convertation one data type into another one
 *
 * Created by John on 10/3/2016.
 */
public interface IConverter<I,O> {
    void init(I input);
    O convert();
}
