package com.example.interview.screens.phonenumbers;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.interview.AppResultReceiver;
import com.example.interview.R;
import com.example.interview.screens.phonenumbers.domain.PhoneNumbersService;
import com.example.interview.storage.Contract;

/**
 * Created by John on 9/11/2016.
 */
public class PhoneNumbersActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>, AppResultReceiver.Callbacks {

    private static final int TOKEN_PHONES = 0;

    private PhoneNumbersPresenter presenter;
    private PhoneNumbersService service;

    private AppResultReceiver resultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_numbers);

        resultReceiver = new AppResultReceiver(new Handler());
        service = new PhoneNumbersService();
        presenter = new PhoneNumbersPresenter(findViewById(R.id.root));

        presenter.showProgress(service.onFirstRun(this, resultReceiver));;

        getLoaderManager().restartLoader(TOKEN_PHONES, Bundle.EMPTY, this);
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
        final String selection = null;
        final String[] args = null;
        final String sortOrder = null;
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
