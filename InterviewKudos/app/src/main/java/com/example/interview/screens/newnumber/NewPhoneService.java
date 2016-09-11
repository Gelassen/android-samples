package com.example.interview.screens.newnumber;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import com.example.interview.AppResultReceiver;
import com.example.interview.converters.storage.TelNumbersToContentValuesConverter;
import com.example.interview.model.TelNumber;
import com.example.interview.storage.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 9/11/2016.
 */
public class NewPhoneService {

    public interface Callbacks {
        void onFinishSave();
    }

    private Callbacks listener;

    public void serListener(Callbacks listener) {
        this.listener = listener;
    }

    public void saveNewPhone(final Context context, final TelNumber telNumber) {
        List<TelNumber> input = new ArrayList<>(1);
        input.add(telNumber);

        TelNumbersToContentValuesConverter converter = new TelNumbersToContentValuesConverter();
        final List<ContentValues> toStorage = converter.convert(input);

        // TODO expose save logic in the separate layer
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                ContentValues contentValues = toStorage.get(0);
                ContentResolver cr = context.getContentResolver();
                cr.insert(Contract.contentUri(Contract.TelNumbers.class), contentValues);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (listener != null) listener.onFinishSave();
            }
        }.execute();
    }
}
