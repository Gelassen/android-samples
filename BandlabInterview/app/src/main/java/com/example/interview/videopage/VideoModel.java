package com.example.interview.videopage;

import android.text.TextUtils;

/**
 * The intent of this class is encapsulate model for video page
 *
 * Created by John on 10/3/2016.
 */
public class VideoModel {

    private String uri;

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public boolean isInvalid() {
        return TextUtils.isEmpty(uri);
    }
}
