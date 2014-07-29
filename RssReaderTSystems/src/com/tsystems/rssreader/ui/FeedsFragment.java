package com.tsystems.rssreader.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tsystems.rssreader.DetailActivity;
import com.tsystems.rssreader.R;
import com.tsystems.rssreader.database.RssReaderContentProvider;
import com.tsystems.rssreader.database.Schema;

public class FeedsFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	public static final int FEEDS_LIST = 0;
	
	private FeedsAdapter adapter;
	
	public static FeedsFragment newInstance() {
		FeedsFragment fragment = new FeedsFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.links_layout, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new FeedsAdapter(getActivity(), 
				R.layout.feed_item, 
				null, 
				CursorAdapter.FLAG_AUTO_REQUERY);
		setListAdapter(adapter);
		
		Intent intent = getActivity().getIntent();
		Bundle bundle = intent.getExtras();
		if (null != bundle && bundle.containsKey(DetailActivity.LINK))
			getLoaderManager().initLoader(FEEDS_LIST, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		Intent intent = getActivity().getIntent();
		final String link = intent.getExtras().getString(DetailActivity.LINK);
		return new CursorLoader(getActivity(), 
				RssReaderContentProvider.FEEDS_URI, 
				new String[] { Schema.Feeds._ID, Schema.Feeds.LINK, Schema.Feeds.GUID, 
							   Schema.Feeds.TITLE, Schema.Feeds.PUB_DATE, Schema.Feeds.DESC,
							   Schema.Feeds.VIEWED}, 
				Schema.Feeds.LINK + "=?", 
				new String[] { link }, 
				null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.changeCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.changeCursor(null);
	}

}
