package com.example.interview.converters.storage;

import android.database.Cursor;

import com.example.interview.converters.IConverter;
import com.example.interview.model.TelNumber;
import com.example.interview.storage.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * The intent of this class is encapsulate detils of conversion from cursor to tel numbers
 *
 * Created by John on 9/11/2016.
 */
public class CursorToTelNumbersConverter implements IConverter<Cursor, List<TelNumber>> {

    private static final int NOT_INIT = -1;

    private int phoneIdx = NOT_INIT;
    private int ownerIdx = NOT_INIT;
    private int priceIdx = NOT_INIT;

    @Override
    public List<TelNumber> convert(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) return new ArrayList<>();

        List<TelNumber> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            TelNumber telNumber = getTelNumber(cursor);
            result.add(telNumber);
        }
        return result;
    }

    private TelNumber getTelNumber(Cursor cursor) {
        TelNumber telNumber = new TelNumber();
        telNumber.setPhoneNumber(cursor.getString(phoneIdx));
        telNumber.setPhoneNumberOwner(cursor.getString(ownerIdx));
        telNumber.setPhoneNumberPrice(cursor.getString(priceIdx));
        return telNumber;
    }

    private void init(Cursor cursor) {
        if (phoneIdx != NOT_INIT) return;

        phoneIdx = cursor.getColumnIndex(Contract.TelNumbers.PHONE_NUMBER);
        ownerIdx = cursor.getColumnIndex(Contract.TelNumbers.PHONE_NUMBER_OWNER);
        priceIdx = cursor.getColumnIndex(Contract.TelNumbers.PHONE_NUMBER_PRICE);
    }
}
