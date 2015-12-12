package com.home.traveller.test;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.home.traveller.App;
import com.home.traveller.storage.Contract;

/**
 * Created by dmitry.kazakov on 12/11/2015.
 */
public class TestActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TestUtil.generateTestData(this);
//        getContentResolver().insert(Contract.contentUri(Contract.TagSample.class), TestUtil.generateTagValues("Comment 2", null));
//        getContentResolver().insert(Contract.contentUri(Contract.TagSample.class), TestUtil.generateTagValues("Comment 3", null));
        getSupportLoaderManager().initLoader(0, Bundle.EMPTY, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final Uri uri = Contract.contentUri(Contract.SampleView.class);
        final String selection = Contract.TagSample.TEST + " IS NOT NULL";
        final String[] selectionArgs = null;//new String[] { "fake" };
        return new CursorLoader(this, uri, null, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(App.TAG, "Data amount: " + cursor.getCount());
        // no op

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // no op
    }
}
