package com.example.interview.model;

import android.database.Cursor;

import com.example.interview.converters.storage.CursorToTelNumberConverter;
import com.example.interview.converters.storage.CursorToTelNumbersConverter;

/**
 * Created by John on 9/11/2016.
 */
public class CursorTelNumbersDatasource implements IDataSource<Cursor, TelNumber> {

    private Cursor cursor;

    private CursorToTelNumberConverter converter;

    public CursorTelNumbersDatasource() {
        converter = new CursorToTelNumberConverter();
    }

    @Override
    public void update(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    @Override
    public TelNumber getItemForPosition(int index) {
        cursor.moveToPosition(index);
        return converter.convert(cursor);
    }
}
