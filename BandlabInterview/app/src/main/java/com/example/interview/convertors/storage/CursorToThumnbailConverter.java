package com.example.interview.convertors.storage;

import android.database.Cursor;

import com.example.interview.convertors.IConverter;
import com.example.interview.model.api.ThumbnailData;
import com.example.interview.storage.Contract;

/**
 * Created by John on 10/3/2016.
 */
public class CursorToThumnbailConverter implements IConverter<Cursor, ThumbnailData> {

    private static final int NOT_INITIALIZED = -1;

    private int idIdx;
    private int uriIdx;
    private int isPreferredIdx;

    private Cursor cursor;

    @Override
    public void init(Cursor input) {
        this.cursor = input;
        idIdx = input.getColumnIndex(Contract.ThumbnailTable.ID);
        uriIdx = input.getColumnIndex(Contract.ThumbnailTable.THUMBNAIL_SOURCE);
        isPreferredIdx = input.getColumnIndex(Contract.ThumbnailTable.IS_PREFERRED);
    }

    @Override
    public ThumbnailData convert() {
        ThumbnailData result = new ThumbnailData();
        result.setId(cursor.getString(idIdx));
        result.setUri(cursor.getString(uriIdx));
        result.setIsPreferred(cursor.getInt(isPreferredIdx) == 1);
        return result;
    }
}
