package com.example.interview.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.interview.model.api.ThumbnailData;

/**
 * Created by John on 10/3/2016.
 */
public class VideoItem implements Parcelable {
    private int id;
    private String source;

    private ThumbnailData thumbnail;

    public VideoItem() {
    }

    protected VideoItem(Parcel in) {
        id = in.readInt();
        source = in.readString();
        thumbnail = (ThumbnailData) in.readSerializable();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ThumbnailData getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ThumbnailData thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(source);
        parcel.writeSerializable(thumbnail);
    }

    public static final Creator<VideoItem> CREATOR = new Creator<VideoItem>() {
        @Override
        public VideoItem createFromParcel(Parcel in) {
            return new VideoItem(in);
        }

        @Override
        public VideoItem[] newArray(int size) {
            return new VideoItem[size];
        }
    };
}
