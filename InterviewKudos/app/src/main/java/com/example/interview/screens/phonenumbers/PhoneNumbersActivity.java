package com.example.interview.screens.phonenumbers;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.interview.App;
import com.example.interview.AppResultReceiver;
import com.example.interview.BaseActivity;
import com.example.interview.Params;
import com.example.interview.R;
import com.example.interview.screens.newnumber.NewPhoneActivity;
import com.example.interview.screens.phonenumbers.domain.PhoneNumbersService;
import com.example.interview.storage.Contract;

/**
 * Created by John on 9/11/2016.
 */
public class PhoneNumbersActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>, AppResultReceiver.Callbacks {

    private static final String EXTRA_SEARCH = ".EXTRA_SEARCH";

    private static final int TOKEN_PHONES = 0;
    private static final int TOKEN_PHONES_SORTED = 1;
    private static final int TOKEN_PHONES_SEARCH = 2;

    private PhoneNumbersPresenter presenter;
    private PhoneNumbersService service;

    private AppResultReceiver resultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO add logic for case if network command failed, offer user to try again

        resultReceiver = new AppResultReceiver(new Handler());
        service = new PhoneNumbersService();
        presenter = new PhoneNumbersPresenter(findViewById(R.id.root));

        presenter.showProgress(service.onFirstRun(this, resultReceiver));;

        getLoaderManager().restartLoader(TOKEN_PHONES, Bundle.EMPTY, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_phone_numbers;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                getLoaderManager().restartLoader(TOKEN_PHONES_SORTED, Bundle.EMPTY, this);
                return true;
            case R.id.action_add_new_phone:
                Intent intent = new Intent(this, NewPhoneActivity.class);
                startActivityForResult(intent, 0);
                return true;
            case R.id.action_search:
                SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                SearchView search = (SearchView) item.getActionView();
                search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
                search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        Log.d(App.TAG, "onQueryTextSubmit: " + s);
                        Bundle bundle = new Bundle();
                        bundle.putString(EXTRA_SEARCH, s);
                        getLoaderManager().restartLoader(TOKEN_PHONES_SEARCH, bundle, PhoneNumbersActivity.this);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String query) {
                        Log.d(App.TAG, "onQueryTextChange: " + query);
                        Bundle bundle = new Bundle();
                        bundle.putString(EXTRA_SEARCH, query);
                        getLoaderManager().restartLoader(TOKEN_PHONES_SEARCH, bundle, PhoneNumbersActivity.this);
                        return true;
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;

        // no op
    }

    @Override
    protected void onResume() {
        super.onResume();
        resultReceiver.setListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        resultReceiver.setListener(null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int token, Bundle bundle) {
        final Uri uri = Contract.contentUri(Contract.TelNumbers.class);
        String selection = null;
        final String[] args = null;
        String sortOrder = null;
        switch (token) {
            case TOKEN_PHONES:
                // no op
                break;
            case TOKEN_PHONES_SORTED:
                sortOrder = Contract.TelNumbers.PHONE_NUMBER_PRICE + " ASC";
                break;
            case TOKEN_PHONES_SEARCH:
                final String search = bundle.getString(EXTRA_SEARCH);
                Log.d(App.TAG, "On search: " + search);
                selection = Contract.TelNumbers.PHONE_NUMBER + " LIKE " + "%" +  search + "%"
                        + " OR " + Contract.TelNumbers.PHONE_NUMBER_OWNER + " LIKE " + "%" +  search + "%"
                        + " OR " + Contract.TelNumbers.PHONE_NUMBER_PRICE + " LIKE " + "%" +  search + "%";
                break;

        }
        return new CursorLoader(this, uri, null, selection, args, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        presenter.updatePhoneNumbers(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        presenter.updatePhoneNumbers(null);
    }

    @Override
    public void onSuccess() {
        presenter.showProgress(false);
    }

    @Override
    public void onFailed() {
        presenter.showProgress(false);
        Toast.makeText(this, getString(R.string.notifications_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkIsNotAvailable() {
        presenter.showProgress(false);
        Toast.makeText(this, getString(R.string.notifications_error_network), Toast.LENGTH_SHORT).show();
    }
}
