package com.home.traveller.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.home.traveller.R;
import com.home.traveller.model.Card;
import com.home.traveller.storage.Contract;

/**
 * Created by dmitry.kazakov on 10/3/2015.
 */
public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String EXTRA_DETAILS = "EXTRA_DETAILS";

    private static final int DATA_DETAILS = 0;
    private static final int DATA_GALLERY = 1;

    public static void startActivity(Context context, Intent data, boolean details) {
        Intent intent = data == null ? new Intent() : new Intent(data);
        intent.setComponent(new ComponentName(context, DetailsActivity.class));
        intent.putExtra(EXTRA_DETAILS, details);
        context.startActivity(intent);
    }

    private DetailsViewBinder detailsViewBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        detailsViewBinder = new DetailsViewBinder(findViewById(R.id.container));

        Launcher launcher = new Launcher();
        launcher.process(getIntent());
    }

    public void onFabClick(View view) {
        // save it
        detailsViewBinder.updateModel();
        finish();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case DATA_DETAILS:
                Uri uri = Contract.contentUri(Contract.CardsTable.class);
                String selection = Contract.CardsTable.IMAGE_PATH + "=?";
                String[] selectionArgs = new String[] { getIntent().getData().toString() };
                return new CursorLoader(this, uri, null, selection, selectionArgs, null);
            case DATA_GALLERY:
                uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";
                return new CursorLoader(this, uri, null, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("Unsupported cursor type");
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // no op
        switch (loader.getId()) {
            case DATA_GALLERY:
                data.moveToFirst();
                final String path = "file://" + data.getString(data.getColumnIndex(MediaStore.Images.Media.DATA));
                detailsViewBinder.updatePath(path);
                detailsViewBinder.loadImage(Uri.parse(path));
                break;
            case DATA_DETAILS:
                if (data.getCount() != 0) {
                    data.moveToFirst();
                    Card card = Card.fromCursorAsItem(data);
                    detailsViewBinder.updateUI(card);
                }
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // no op
    }

    private class Launcher {

        public void process(Intent intent) {
            Uri data = intent.getData();
            if (data != null) {
                processDetails(data);
            } else {
                processFromGallery();
            }
        }

        private void processFromGallery() {
            getSupportLoaderManager().initLoader(DATA_GALLERY, Bundle.EMPTY, DetailsActivity.this);
        }

        private void processDetails(Uri data) {
            detailsViewBinder.updatePath(data.toString());
            detailsViewBinder.loadImage(data);
            // TODO go to storage and take the others dat by uri
            getSupportLoaderManager().initLoader(DATA_DETAILS, Bundle.EMPTY, DetailsActivity.this);
        }
    }
}
