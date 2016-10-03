package com.example.interview.convertors.storage;

import android.database.Cursor;
import android.util.Log;

import com.example.interview.App;
import com.example.interview.convertors.IConverter;
import com.example.interview.model.VideoItem;
import com.example.interview.storage.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * The intent of this class is encapsulate coversion from cursor to Video object
 *
 * Created by John on 10/3/2016.
 */
public class CursorToVideoConverter implements IConverter<Cursor, List<VideoItem>> {

    private static final int NOT_INITIALIZED = -1;

    private int idIdx = NOT_INITIALIZED;
    private int sourceIdx = NOT_INITIALIZED;
    private int thumbnailIsPreferedIdx = NOT_INITIALIZED;

    private CursorToThumnbailConverter thumbConverter;

    private Cursor cursor;

    @Override
    public void init(Cursor input) {
        this.idIdx = input.getColumnIndex(Contract.VideoTable.ID);
        this.sourceIdx = input.getColumnIndex(Contract.VideoTable.VIDEO_SOURCE);
        this.thumbnailIsPreferedIdx = input.getColumnIndex(Contract.ThumbnailTable.IS_PREFERRED);
        this.thumbConverter = new CursorToThumnbailConverter();
        this.cursor = input;
    }

    @Override
    public List<VideoItem> convert() {
        List<VideoItem> result = new ArrayList<>();
        String[] names = cursor.getColumnNames();
        while (cursor.moveToNext()) {
            VideoItem video = new VideoItem();
            video.setId(cursor.getInt(idIdx));
            video.setSource(cursor.getString(sourceIdx));

            thumbConverter.init(cursor);
            video.setThumbnail(thumbConverter.convert());

            result.add(video);
        }
        return result;
    }
}
