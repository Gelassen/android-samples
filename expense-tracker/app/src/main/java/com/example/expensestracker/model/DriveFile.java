package com.example.expensestracker.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gelassen on 02.03.2018.
 */

public class DriveFile {

    @SerializedName("id")
    private String id;

    @SerializedName("downloadUrl")
    private String downloadUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
