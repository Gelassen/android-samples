package com.example.interview.mainpage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.interview.model.VideoItem;
import com.example.interview.model.api.VideoData;
import com.example.interview.videopage.VideoPageFragment;

import java.util.List;

/**
 * The intent of this class is supply view pager with list of fragments
 *
 * Created by John on 10/3/2016.
 */
public class VideoPageAdapter extends FragmentStatePagerAdapter {

    private IDataSource<List<VideoItem>,VideoItem> dataSource;

    public VideoPageAdapter(FragmentManager fm) {
        super(fm);
        dataSource = new VideoDataSource();
    }

    public void update(List<VideoItem> model) {
        dataSource.update(model);
    }

    @Override
    public Fragment getItem(int position) {
        VideoItem videoData = dataSource.getItemForPosition(position);
        return VideoPageFragment.newInstance(videoData, position);
    }

    @Override
    public int getCount() {
        return dataSource.getCount();
    }
}
