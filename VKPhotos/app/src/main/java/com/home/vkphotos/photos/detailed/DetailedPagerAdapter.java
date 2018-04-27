package com.home.vkphotos.photos.detailed;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.home.vkphotos.AppApplication;
import com.home.vkphotos.utils.ImageFetcher;
import com.home.vkphotos.photos.model.Item;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DetailedPagerAdapter extends FragmentPagerAdapter {

    private List<Item> data = new ArrayList<>();

    @Inject
    ImageFetcher imageFetcher;

    public DetailedPagerAdapter(FragmentManager fm, AppApplication app) {
        super(fm);
        (app.getAppComponent()).inject(this);
    }

    public void setData(List<Item> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Item item = data.get(position);
        DetailedFragment fragment = DetailedFragment.newInstance(item);
        fragment.setImageFetcher(imageFetcher);
        return fragment;
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
