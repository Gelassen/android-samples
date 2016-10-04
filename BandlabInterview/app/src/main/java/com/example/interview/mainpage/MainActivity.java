package com.example.interview.mainpage;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.interview.R;
import com.example.interview.convertors.storage.CursorToVideoConverter;
import com.example.interview.model.VideoItem;
import com.example.interview.network.commands.GetInitialVideoPageCommand;
import com.example.interview.storage.Contract;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int TOKEN_VIEW = 0;

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

        getLoaderManager().restartLoader(TOKEN_VIEW, Bundle.EMPTY, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int token, Bundle bundle) {
        Uri uri = null;
        switch (token) {
            case TOKEN_VIEW:
                uri = Contract.contentUri(Contract.VideoView.class);
                break;
        }
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case TOKEN_VIEW:
                converter.init(cursor);
                model = converter.convert();
                presenter.updateModel(model);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // no op
    }
}
