package com.home.traveller.domain;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.home.traveller.model.Card;
import com.home.traveller.storage.Contract;

/**
 * Created by dmitry.kazakov on 10/25/2015.
 */
public class StorageImageSource implements ImageSource, LoaderManager.LoaderCallbacks<Cursor> {

    private AppCompatActivity context;
    private ImageSource.Listener listener;

    private Card card;

    public StorageImageSource(AppCompatActivity context, Listener listener, Card card) {
        this.context = context;
        this.listener = listener;
        this.card = card;
    }

    @Override
    public void perform() {
        // got to storage for more details
        LoaderManager loaderManager = context.getSupportLoaderManager();
        loaderManager.initLoader(0, Bundle.EMPTY, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Contract.contentUri(Contract.CardsTable.class);
        String selection = Contract.CardsTable.IMAGE_PATH + "=?";
        String[] selectionArgs = new String[] { card.getPath() };
        return new CursorLoader(context, uri, null, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            Card card = Card.fromCursorAsItem(data);
            if (listener != null) listener.onUrlPrepared(card);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // no op
    }
}
