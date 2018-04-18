package com.home.vkphotos.photos.detailed;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.home.vkphotos.photos.Item;

import java.util.ArrayList;
import java.util.List;

public class DetailedPagerAdapter extends FragmentPagerAdapter {

    private List<Item> data = new ArrayList<>();

    public DetailedPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<Item> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Item item = data.get(position);
        return DetailedFragment.newInstance(item);
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
