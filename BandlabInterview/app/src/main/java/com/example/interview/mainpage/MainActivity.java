package com.example.interview.mainpage;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.interview.App;
import com.example.interview.R;
import com.example.interview.convertors.storage.CursorToVideoConverter;
import com.example.interview.mainpage.MainPagerPresenter;
import com.example.interview.mainpage.VideoPageListener;
import com.example.interview.model.VideoItem;
import com.example.interview.network.commands.GetInitialVideoPageCommand;
import com.example.interview.storage.Contract;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int TOKEN_VIEW = 0;
    private static final int TOKEN_VIDEO = 1;
    private static final int TOKEN_THUMBS = 2;

    private CursorToVideoConverter converter;

    private List<VideoItem> model;

    private MainPagerPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPagerPresenter(
                findViewById(R.id.viewpager),
                getSupportFragmentManager()
        );
        presenter.addOnPageListener(new VideoPageListener());
        converter = new CursorToVideoConverter();


        new GetInitialVideoPageCommand()
                .start(this, null);

//        getLoaderManager().restartLoader(TOKEN_THUMBS, Bundle.EMPTY, this);
//        getLoaderManager().restartLoader(TOKEN_VIDEO, Bundle.EMPTY, this);
        getLoaderManager().restartLoader(TOKEN_VIEW, Bundle.EMPTY, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int token, Bundle bundle) {
        Uri uri = null;
        switch (token) {
            case TOKEN_VIEW:
                uri = Contract.contentUri(Contract.VideoView.class);
                break;
            case TOKEN_VIDEO:
                uri = Contract.contentUri(Contract.VideoTable.class);
                break;
            case TOKEN_THUMBS:
                uri = Contract.contentUri(Contract.ThumbnailTable.class);
                break;
        }
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case TOKEN_THUMBS:
                int thumbs = cursor.getCount();
                Log.d(App.TAG, "Thumbs: " + thumbs);
                break;
            case TOKEN_VIDEO:
                int videos = cursor.getCount();
                Log.d(App.TAG, "Videos: " + videos);
                break;
            case TOKEN_VIEW:
                int tokens = cursor.getCount();
                Log.d(App.TAG, "View: " + tokens);
                CursorToVideoConverter converter = new CursorToVideoConverter();
                converter.init(cursor);

                model = converter.convert();
                presenter.updateModel(model);
                break;
        }
//        converter.init(cursor);
//        model = converter.convert();
//        Log.d(App.TAG, "Video model size: " + model.size());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // no op
    }
}
