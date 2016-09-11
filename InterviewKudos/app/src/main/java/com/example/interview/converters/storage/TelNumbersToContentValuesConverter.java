package com.example.interview.converters.storage;

import android.content.ContentValues;

import com.example.interview.converters.IConverter;
import com.example.interview.model.TelNumber;
import com.example.interview.storage.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * The intent of this class is encapsulate details of conversion from TelNumbers to
 * ContentValues to save into the storage
 *
 * Created by John on 9/11/2016.
 */
public class TelNumbersToContentValuesConverter implements IConverter<List<TelNumber>, List<ContentValues>> {

    @Override
    public List<ContentValues> convert(List<TelNumber> input) {
        if (input == null || input.size() == 0) return new ArrayList<>();

        final List<ContentValues> result = new ArrayList<>(input.size());
        final int size = input.size();
        for (int idx = 0; idx < size; idx++) {
            TelNumber telNumber = input.get(idx);
            result.add(convert(telNumber));
        }

        return result;
    }

    private ContentValues convert(TelNumber telNumber) {
        ContentValues contentValues = new ContentValues(3);
        contentValues.put(Contract.SampleTable.PHONE_NUMBER, telNumber.getPhoneNumber());
        contentValues.put(Contract.SampleTable.PHONE_NUMBER_PRICE, telNumber.getPhoneNumberPrice());
        contentValues.put(Contract.SampleTable.PHONE_NUMBER_OWNER, telNumber.getPhoneNumberOwner());
        return contentValues;
    }
}
