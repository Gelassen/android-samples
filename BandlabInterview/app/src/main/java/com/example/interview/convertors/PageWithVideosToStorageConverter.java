package com.example.interview.convertors;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.example.interview.model.api.ThumbnailData;
import com.example.interview.model.api.VideoData;
import com.example.interview.storage.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * The intent of this class is encapsulate transformation of video response to data in storage
 *
 * Created by John on 10/3/2016.
 */
public class PageWithVideosToStorageConverter implements IConverter<List<VideoData>, Boolean> {

    private ContentResolver cr;
    private List<VideoData> input;

    private List<ContentValues> videos;
    private List<ContentValues> thumbnails;


    public PageWithVideosToStorageConverter(Context context) {
        this.cr = context.getContentResolver();
    }

    @Override
    public void init(List<VideoData> input) {
        this.input = input;
        videos = new ArrayList<>();
        thumbnails = new ArrayList<>();
    }

    @Override
    public Boolean convert() {
        if (input == null) return false;

        for (VideoData video : input) {
            videos.add(convertVideo(video));
            for (ThumbnailData thumb : video.getThumbnails().getData()) {
                thumbnails.add(convertThumbnail(thumb, video.getId()));
            }
        }

        save();

        return true;
    }

    private ContentValues convertThumbnail(ThumbnailData thumb, final String videoId) {
        ContentValues contentValues = new ContentValues(7);
        contentValues.put(Contract.ThumbnailTable.ID, thumb.getId());
        contentValues.put(Contract.ThumbnailTable.HEIGHT, thumb.getHeight());
        contentValues.put(Contract.ThumbnailTable.IS_PREFERRED, thumb.getIsPreferred() ? 1 : 0);
        contentValues.put(Contract.ThumbnailTable.SCALE, thumb.getScale());
        contentValues.put(Contract.ThumbnailTable.THUMBNAIL_SOURCE, thumb.getUri());
        contentValues.put(Contract.ThumbnailTable.VIDEO_ID, videoId);
        contentValues.put(Contract.ThumbnailTable.WIDTH, thumb.getWidth());
        return contentValues;
    }

    private ContentValues convertVideo(VideoData video) {
        ContentValues contentValues = new ContentValues(2);
        contentValues.put(Contract.VideoTable.ID, video.getId());
        contentValues.put(Contract.VideoTable.VIDEO_SOURCE, video.getSource());
        return contentValues;
    }

    private void save() {
        ContentValues[] videoContentValues = new ContentValues[videos.size()];
        videos.toArray(videoContentValues);
        cr.bulkInsert(Contract.contentUri(Contract.VideoTable.class), videoContentValues);

        ContentValues[] thumbContentValues = new ContentValues[thumbnails.size()];
        thumbnails.toArray(thumbContentValues);
        cr.bulkInsert(Contract.contentUri(Contract.ThumbnailTable.class), thumbContentValues);
    }
}
