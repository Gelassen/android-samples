package com.example.interview.mainpage;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.interview.AppResultReceiver;
import com.example.interview.R;
import com.example.interview.convertors.storage.CursorToVideoConverter;
import com.example.interview.model.VideoItem;
import com.example.interview.network.Status;
import com.example.interview.network.commands.GetInitialVideoPageCommand;
import com.example.interview.storage.Contract;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,AppResultReceiver.Callbacks {

    private static final int TOKEN_VIEW = 0;

    private CursorToVideoConverter converter;
    private List<VideoItem> model;
    private MainPagerPresenter presenter;

    private AppResultReceiver resultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPagerPresenter(
                findViewById(R.id.root),
                getSupportFragmentManager()
        );
        presenter.addOnPageListener(new VideoPageListener(this));
        converter = new CursorToVideoConverter();

        resultReceiver = new AppResultReceiver(new Handler());
        resultReceiver.setListener(this);
        new GetInitialVideoPageCommand()
                .start(this, resultReceiver);
        presenter.showPlaceholder(true);

        getLoaderManager().restartLoader(TOKEN_VIEW, Bundle.EMPTY, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (resultReceiver.isNotListen()) resultReceiver.setListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        resultReceiver.setListener(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.removePageLisener();
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

    @Override
    public void onSuccess(Status status) {
        presenter.showPlaceholder(false);
    }

    @Override
    public void onError(Status status) {
        presenter.showPlaceholder(false);
        switch (status.getStatusCode()) {
            case Status.FAILED_NETWORK:
                Toast.makeText(this, "Do you turn on your network?", Toast.LENGTH_SHORT).show();
                break;
            case Status.FAILED_TO_EXECUTE_REQUEST:
                Toast.makeText(this, "Please try run app later", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
