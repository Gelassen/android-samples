package com.example.interview.videopage;

import android.text.TextUtils;
import android.util.Log;

import com.example.interview.App;
import com.example.interview.model.api.ThumbnailData;

/**
 * The intent of this class is encapsulate model for video page
 *
 * Created by John on 10/3/2016.
 */
public class VideoModel {

    private String uri;
    private ThumbnailData placeholderUri;

    private boolean isFirstStart;
    private boolean isInvalidState;
    private boolean isPaused;

    private int currentPosition;

    public VideoModel() {
        isFirstStart = true;
        isInvalidState = false;
        isPaused = false;
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
        Log.d(App.TAG, "" + isInvalidState);
        return TextUtils.isEmpty(uri) || isInvalidState;
    }

    public boolean isFirstStart() {
        return isFirstStart;
    }

    public void firstStartHappened() {
        Log.d(App.TAG, "firstStartHappened for " + uri);
        isFirstStart = false;
    }

    public void saveProgressState(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getSavedPosition() {
        return currentPosition;
    }


    /**
     * Can be caused by invalid data model or invalid app state, e.g. destroyed activity
     * */
    public void  setInvalidState(boolean isInvalidState) {
        Log.d(App.TAG, "" + isInvalidState);
        this.isInvalidState = isInvalidState;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }
}
