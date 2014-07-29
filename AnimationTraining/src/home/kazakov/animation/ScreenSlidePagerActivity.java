package home.kazakov.animation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class ScreenSlidePagerActivity extends FragmentActivity {

	private ViewPager viewPager;
	
	private PagerAdapter adapter;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_main);
		
		viewPager = (ViewPager) findViewById(R.id.pager);
		adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);		
	}
	
    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
        	viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }
	
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg) {
			return new ScreenSlidePageFragment();
		}

		@Override
		public int getCount() {
			return 3;
		}
		
	}
	
	
	
}
