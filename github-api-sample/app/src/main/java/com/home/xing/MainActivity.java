package com.home.xing;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.home.xing.network.commands.GetReposCommand;
import com.home.xing.storage.Contract;
import com.home.xing.ui.RepoAdapter;


public class MainActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int REPO_LOADER = 0;
    private RepoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        adapter = new RepoAdapter(this, R.layout.list_item, null);
        setListAdapter(adapter);

        getListView().setLongClickable(true);
        getListView().setOnItemLongClickListener(clickListener);

        getLoaderManager().initLoader(REPO_LOADER, null, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_upload) {
            final int firstPage = 1;
            new GetReposCommand(firstPage, RepoAdapter.ITEM_PER_PAGE).start(this, null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final String sort = Contract.RepoTable.CACHED + " ASC";
        return new CursorLoader(this, Contract.contentUri(Contract.RepoTable.class), null, null, null, sort);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    // TODO replace it with moder DialogFragment
    @Override
    protected Dialog onCreateDialog(int id, final Bundle args) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_choose_link)
                .setItems(R.array.repos, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final String[] urls = new Urls().fromBundle(args);
                        Uri uri = Uri.parse(urls[which]);
                        String title = getString(R.string.title_choose_browser);
                        startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, uri), title));
                    }
                });
        return builder.create();

    }

    private AdapterView.OnItemLongClickListener clickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            RepoAdapter.ViewHolder holder = (RepoAdapter.ViewHolder) view.getTag();
            showDialog(0, new Urls().asBundle(holder.getRepoUrl(), holder.getOwnerUrl()));
            return true;
        }
    };

    private class Urls {
        public final static String REPO_URL = "REPO_URL";
        public final static String OWNER_URL = "OWNER_URL";

        public Bundle asBundle(final String repo, final String owner) {
            Bundle bundle = new Bundle(2);
            bundle.putString(REPO_URL, repo);
            bundle.putString(OWNER_URL, owner);
            return bundle;
        }

        public String[] fromBundle(Bundle bundle) {
            return new String[] { bundle.getString(REPO_URL), bundle.getString(OWNER_URL)};
        }

    }
}
