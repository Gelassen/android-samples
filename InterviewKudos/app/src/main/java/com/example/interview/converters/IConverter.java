package com.example.interview.converters;

/**
 * The intent of this class is provide interface for conversion one data type
 * to another one. Classes which apply this interface will provide data about data types
 * and implementation details
 *
 * Created by John on 9/11/2016.
 */
public interface IConverter<I, O> {
    O convert(I input);
}
