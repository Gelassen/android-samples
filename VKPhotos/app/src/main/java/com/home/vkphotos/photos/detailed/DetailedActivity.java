package com.home.vkphotos.photos.detailed;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.home.vkphotos.AppApplication;
import com.home.vkphotos.BaseActivity;
import com.home.vkphotos.utils.ImageFetcher;
import com.home.vkphotos.R;
import com.home.vkphotos.photos.model.Item;

import java.util.ArrayList;
import java.util.List;

public class DetailedActivity extends BaseActivity {

    private static final String EXTRA_DATA = "EXTRA_DATA";
    private static final String EXTRA_SELECTED = "EXTRA_SELECTED";

    public static void start(Context context, ArrayList<Item> items, int id) {
        Intent intent = new Intent(context, DetailedActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_DATA, items);
        intent.putExtra(EXTRA_SELECTED, id);
        context.startActivity(intent);
    }

    private DetailedPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        getSupportActionBar().hide();

        final int id = getIntent().getExtras().getInt(EXTRA_SELECTED);
        ArrayList<Item> items = getIntent().<Item>getParcelableArrayListExtra(EXTRA_DATA);

        adapter = new DetailedPagerAdapter(getSupportFragmentManager(), (AppApplication) getApplication());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        adapter.setData(getIntent().<Item>getParcelableArrayListExtra(EXTRA_DATA));

        viewPager.setCurrentItem(getSelectedPosition(items, id));
    }

    private int getSelectedPosition(List<Item> items, final int selectedId) {
        for (int idx = 0; idx < items.size(); idx++) {
            if (items.get(idx).getId() == selectedId) {
                return idx;
            }
        }
        return 0;
    }
}
