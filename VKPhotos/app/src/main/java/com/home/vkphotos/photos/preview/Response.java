package com.home.vkphotos.photos.preview;


import com.google.gson.annotations.SerializedName;
import com.home.vkphotos.photos.model.Item;

import java.util.List;

public class Response {

    @SerializedName("count")
    private Integer count;
    @SerializedName("items")
    private List<Item> items = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
