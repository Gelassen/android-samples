package com.example.expensestracker.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gelassen on 02.03.2018.
 */

public class DriveFiles {

    @SerializedName("kind")
    private String kind;
    @SerializedName("etag")
    private String etag;
    @SerializedName("selfLink")
    private String selfLink;
    @SerializedName("nextPageToken")
    private String nextPageToken;
    @SerializedName("nextLink")
    private String nextLink;
    @SerializedName("incompleteSearch")
    private boolean incompleteSearch;
    @SerializedName("items")
    private List<DriveFile> items;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }

    public boolean isIncompleteSearch() {
        return incompleteSearch;
    }

    public void setIncompleteSearch(boolean incompleteSearch) {
        this.incompleteSearch = incompleteSearch;
    }

    public List<DriveFile> getItems() {
        return items;
    }

    public void setItems(List<DriveFile> items) {
        this.items = items;
    }
}
