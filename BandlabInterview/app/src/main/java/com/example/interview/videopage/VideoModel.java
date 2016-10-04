package com.example.interview.videopage;

import android.text.TextUtils;

import com.example.interview.model.api.ThumbnailData;

/**
 * The intent of this class is encapsulate model for video page
 *
 * Created by John on 10/3/2016.
 */
public class VideoModel {

    private String uri;
    private boolean isFirstStart;
    private ThumbnailData placeholderUri;

    public VideoModel() {
        isFirstStart = true;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setPlaceholderUri(ThumbnailData placeholderUri) {
        this.placeholderUri = placeholderUri;
    }

    public String getPlaceholderUri() {
        return placeholderUri.getUri();
    }

    public boolean isInvalid() {
        return TextUtils.isEmpty(uri);
    }

    public boolean isFirstStart() {
        return isFirstStart;
    }

    public void firstStartHappened() {
        isFirstStart = false;
    }

}
