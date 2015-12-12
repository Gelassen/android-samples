package com.home.traveller.domain;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.home.traveller.ui.CardController;

/**
 * Created by dmitry.kazakov on 10/25/2015.
 */
public class CameraImageSource implements ImageSource, LoaderManager.LoaderCallbacks<Cursor> {

    private AppCompatActivity context;

    private ImageSource.Listener listener;

    public CameraImageSource(AppCompatActivity context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void perform() {
        // get image from storage and make the copy
        LoaderManager loaderManager = context.getSupportLoaderManager();
        loaderManager.initLoader(0, Bundle.EMPTY, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";
        return new CursorLoader(context, uri, null, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        // copy image to right location
        final String path = "file://" + data.getString(data.getColumnIndex(MediaStore.Images.Media.DATA));
        CardController cardController = new CardController();
        cardController.updateImagePath(path);
        cardController.createNew(context, listener);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // no op
    }
}
