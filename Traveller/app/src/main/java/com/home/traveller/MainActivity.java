package com.home.traveller;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.home.traveller.domain.ImageSource;
import com.home.traveller.model.Card;
import com.home.traveller.model.DialogOption;
import com.home.traveller.storage.Contract;
import com.home.traveller.ui.CardController;
import com.home.traveller.ui.CardsAdapter;
import com.home.traveller.ui.DetailsActivity;
import com.home.traveller.utils.FileManager;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, ImageSource.Listener{

    private static final int GET_IMAGE = 0;
    private CardsAdapter adapter;

    private DetailsActivity.ImageSourceConst imageSourceConst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) setSupportActionBar(toolbar);

        adapter = new CardsAdapter();
        adapter.setHasStableIds(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        getSupportLoaderManager().restartLoader(0, Bundle.EMPTY, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_photo) {
            final List<DialogOption> options = new ArrayList<>(2);
            options.add(new DialogOption("Make a new photo", DialogOption.TYPE_CAMERA));
            options.add(new DialogOption("Take from gallery", DialogOption.TYPE_GALLERY));

            String[] optionsAsStrings = new String[options.size()];
            optionsAsStrings[0] = options.get(0).getName();
            optionsAsStrings[1] = options.get(1).getName();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose photo");
            builder.setItems(optionsAsStrings, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO write a consts to imageSourceConst
                    DialogOption option = options.get(which);
                    Intent intent = option.getIntent();
                    startActivityForResult(intent, GET_IMAGE);
                }
            });

            builder.create().show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;

        boolean fromGallery = data != null;
        if (fromGallery) {
//            final File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//            FileManager fileManager = new FileManager(this);
//            File file = fileManager.copyFile(
//                    getFileDescriptor(data),
//                    fileManager.addHiddenFolderToPath(directory)
//            );
//
//            Log.d(App.TAG, "File card: " + file.exists());
            Card card = new Card();
            card.setFileDescriptor(getFileDescriptor(data));
            CardController cardController = new CardController();
            cardController.setCard(card);
            cardController.createNew(this, this);
        } else {
            DetailsActivity.ImageSourceConst source = data != null ? DetailsActivity.ImageSourceConst.CALLERY : DetailsActivity.ImageSourceConst.CAMERA;
            DetailsActivity.startActivity(this, data, source);
        }
    }

    private FileDescriptor getFileDescriptor(Intent data) {
        ParcelFileDescriptor descriptor = null;
        Uri path = data.getData();
        try {
            descriptor = getContentResolver().openFileDescriptor(path, "r");
        } catch (FileNotFoundException e) {
            Log.e(App.TAG, "File not found for " + path, e);
        }
        return descriptor.getFileDescriptor();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Contract.contentUri(Contract.CardsTable.class), null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(App.TAG, "Cards: " + data.getCount());
        adapter.update(Card.fromCursorAsList(data));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.update(null);
    }

    @Override
    public void onUrlPrepared(Card card) {
        DetailsActivity.startActivity(this, Uri.parse(card.getPath()), DetailsActivity.ImageSourceConst.CALLERY);
    }
}
