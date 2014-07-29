package com.tsystems.rssreader;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class DetailActivity extends FragmentActivity {
	
	public final static String LINK = "link";
	
	private String url;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.feeds_layout);
	}
	
	

}
