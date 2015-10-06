package com.home.traveller;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

import com.home.traveller.model.Card;
import com.home.traveller.model.DialogOption;
import com.home.traveller.storage.Contract;
import com.home.traveller.ui.CardsAdapter;
import com.home.traveller.ui.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int GET_IMAGE = 0;
    private CardsAdapter adapter;

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
//        if (resultCode == RESULT_CANCELED) return;
        DetailsActivity.startActivity(this, data, false);
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
}
