package com.dataart.school.appinterface;

import com.dataart.school.R;
import com.dataart.school.R.id;
import com.dataart.school.R.layout;
import com.dataart.school.appinterface.fragments.StoresFragment;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class MainActivity extends FragmentActivity {

    private ViewPager pager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	pager = (ViewPager) findViewById(R.id.stores_pager);

	pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

	    @Override
	    public int getCount() {
		return 2;
	    }

	    @Override
	    public Fragment getItem(int position) {
		return StoresFragment.newFragment(position);
	    }
	});

	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	    actionBarStuff();
	}
    }

    @TargetApi(11)
    private void actionBarStuff() {
	ActionBar ab = getActionBar();
	ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	ab.addTab(ab.newTab().setText("Ascending").setTabListener(new MyTabListener(0)));
	ab.addTab(ab.newTab().setText("Descending").setTabListener(new MyTabListener(1)));

	pager.setOnPageChangeListener(new OnPageChangeListener() {

	    @Override
	    public void onPageSelected(int position) {
		getActionBar().setSelectedNavigationItem(position);
	    }

	    @Override
	    public void onPageScrolled(int arg0, float arg1, int arg2) {
	    }

	    @Override
	    public void onPageScrollStateChanged(int arg0) {
	    }
	});
    }

    @TargetApi(11)
    private class MyTabListener implements TabListener {

	private int pos;

	public MyTabListener(int pos) {
	    this.pos = pos;
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
	    pager.setCurrentItem(pos);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}
    };
}
