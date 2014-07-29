package com.dataart.school.appinterface.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dataart.school.CustomContentProvider;
import com.dataart.school.R;
import com.dataart.school.Schema;
import com.dataart.school.R.id;
import com.dataart.school.R.layout;
import com.dataart.school.R.menu;
import com.dataart.school.Schema.Stores;
import com.dataart.school.appinterface.StoreCursorAdapter;
import com.dataart.school.webservice.GetStoresCommand;
import com.dataart.school.webservice.WebService;

public class StoresFragment extends Fragment implements LoaderCallbacks<Cursor> {

    private static final String ARG_TYPE = "arg_type";

    public static int TYPE_ASC = 0;

    public static int TYPE_DESC = 1;

    private StoreCursorAdapter adapter;

    private int type = TYPE_ASC;

    public static StoresFragment newFragment(int type) {
	Bundle args = new Bundle();
	args.putInt(ARG_TYPE, type);

	StoresFragment storesFragment = new StoresFragment();
	storesFragment.setArguments(args);
	return storesFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);

	ListView list = (ListView) getView().findViewById(android.R.id.list);
	adapter = new StoreCursorAdapter(getActivity(), R.layout.store_item, null, 0);
	list.setAdapter(adapter);

	type = getArguments().getInt(ARG_TYPE);

	Bundle args = new Bundle();
	args.putInt(ARG_TYPE, type);
	getLoaderManager().initLoader(0, args, this);

	setHasOptionsMenu(type == TYPE_ASC);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	return inflater.inflate(R.layout.fragment_stores, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	super.onCreateOptionsMenu(menu, inflater);

	inflater.inflate(R.menu.activity_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case R.id.menu_settings:
	    startLoadCommand();
	    break;
	}
	return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int token, Bundle bundle) {
	int tp = bundle.getInt(ARG_TYPE);

	return new CursorLoader(getActivity(), CustomContentProvider.STORES_URI, null, null, null,
		Schema.Stores.STORE_NAME + (tp == TYPE_ASC ? " ASC" : " DESC"));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
	adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
	adapter.swapCursor(null);
    }

    private void startLoadCommand() {
	Intent intent = new Intent(getActivity(), WebService.class);
	intent.putExtra(WebService.COMMAND, new GetStoresCommand());
	getActivity().startService(intent);
    }

}
