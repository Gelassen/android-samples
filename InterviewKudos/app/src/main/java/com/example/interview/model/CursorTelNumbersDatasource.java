package com.example.interview.model;

import android.database.Cursor;

/**
 * Created by John on 9/11/2016.
 */
public class CursorTelNumbersDatasource implements IDataSource<Cursor, TelNumber> {

    private Cursor cursor;

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
        return null;
    }
}
