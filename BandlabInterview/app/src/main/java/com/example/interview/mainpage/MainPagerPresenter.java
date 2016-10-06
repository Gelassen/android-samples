package com.example.interview.mainpage;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

import com.example.interview.R;
import com.example.interview.model.VideoItem;

import java.util.List;

/**
 * The intent of this class is encapsulate UI for main page and expose API in
 * terms of biz logic for the host
 *
 * Created by John on 10/3/2016.
 */
public class MainPagerPresenter {
    private ViewPager viewPager;
    private VideoPageAdapter adapter;

    private ProgressBar placeholder;

    private ViewPager.OnPageChangeListener listener;

    public MainPagerPresenter(View view, FragmentManager fm) {
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        placeholder = (ProgressBar) view.findViewById(R.id.progress);
        adapter = new VideoPageAdapter(fm);
        viewPager.setAdapter(adapter);
    }

    public void addOnPageListener(ViewPager.OnPageChangeListener listener) {
        this.listener = listener;
        viewPager.addOnPageChangeListener(listener);
    }

    public void removePageLisener() {
        viewPager.removeOnPageChangeListener(listener);
    }

    public void showPlaceholder(boolean show) {
        placeholder.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void updateModel(List<VideoItem> model) {
        adapter.update(model);
        adapter.notifyDataSetChanged();
    }
}
