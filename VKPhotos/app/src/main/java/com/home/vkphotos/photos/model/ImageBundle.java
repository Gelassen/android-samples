package com.home.vkphotos.photos.model;


public class ImageBundle {
    private String url;
    private int id;
    private boolean isDetailedView;

    public ImageBundle() {
        this.isDetailedView = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDetailedView() {
        isDetailedView = true;
    }

    public boolean isDetailed() {
        return isDetailedView;
    }
}
