package com.tsystems.rssreader.ui;

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

import com.tsystems.rssreader.R;
import com.tsystems.rssreader.database.RssReaderContentProvider;
import com.tsystems.rssreader.database.Schema;

public class LinksFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	public static final int LINKS_LIST = 0;
	
	private LinksAdapter adapter;
	
	public static LinksFragment newInstance() {
		LinksFragment fragment = new LinksFragment();
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
		adapter = new LinksAdapter(getActivity(), 
				R.layout.link_item, 
				null, 
				CursorAdapter.FLAG_AUTO_REQUERY);
		setListAdapter(adapter);
		
		getLoaderManager().initLoader(LINKS_LIST, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg, Bundle bundle) {
		return new CursorLoader(getActivity(), 
				RssReaderContentProvider.LINKS_URI, 
				new String[] { Schema.Links._ID, Schema.Links.LINK, Schema.Links.TITLE }, 
				null, 
				null, 
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
