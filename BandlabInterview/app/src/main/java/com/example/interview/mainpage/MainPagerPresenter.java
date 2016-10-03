package com.example.interview.mainpage;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.interview.R;

/**
 * The intent of this class is encapsulate UI for main page and expose API in
 * terms of biz logic for the host
 *
 * Created by John on 10/3/2016.
 */
public class MainPagerPresenter {
    private ViewPager viewPager;

    public MainPagerPresenter(View view, FragmentManager fm) {
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new VideoPageAdapter(fm));
    }
}
