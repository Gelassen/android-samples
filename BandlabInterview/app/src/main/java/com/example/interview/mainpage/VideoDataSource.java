package com.example.interview.mainpage;

import com.example.interview.model.VideoItem;
import com.example.interview.model.api.VideoData;

import java.util.List;

/**
 * The intent of this class is incapsulate model for VideoDataSource implements as collection
 *
 * Created by John on 10/3/2016.
 */
public class VideoDataSource implements IDataSource<List<VideoItem>, VideoItem> {

    private List<VideoItem> model;

    @Override
    public void update(List<VideoItem> model) {
        this.model = model;
    }

    @Override
    public int getCount() {
        return model == null ? 0 : model.size();
    }

    @Override
    public VideoItem getItemForPosition(int position) {
        return model.get(position);
    }
}
