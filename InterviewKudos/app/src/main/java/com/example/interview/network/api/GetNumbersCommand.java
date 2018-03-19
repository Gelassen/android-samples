package com.example.interview.network.api;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;
import android.util.Log;

import com.example.interview.App;
import com.example.interview.R;
import com.example.interview.converters.network.JsonToTelNumberConverter;
import com.example.interview.converters.storage.TelNumbersToContentValuesConverter;
import com.example.interview.model.TelNumber;
import com.example.interview.network.HttpCommand;
import com.example.interview.network.Status;
import com.example.interview.storage.Contract;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * The intent of this class is encapsulate details of communication with
 * server and database when client fetch new tel numbers
 *
 * Created by John on 9/11/2016.
 */
public class GetNumbersCommand extends HttpCommand {

    public GetNumbersCommand() {
    }

    protected GetNumbersCommand(Parcel in) {
        // no op
    }

    @Override
    protected void onPreExecute(Context context) {
        final String url = context.getString(R.string.api);
        request = new Request.Builder().url(url).build();
    }

    @Override
    protected void onProcessResponse(Response response) {
        Log.d(App.TAG, "onProcessResponse for GetNumbersCommand");
        try {
            // simply drop clean table for now, in future add unique constraint with auto update mode
            ContentResolver cr = context.getContentResolver();
            cr.delete(Contract.contentUri(Contract.TelNumbers.class), null, null);

            // TODO please note
            // for current amount of data it is safe to use in-memory parser, however
            // with growth of response client should use pull parser. Use current solution to speed up development
            String jsonStr = response.body().string();
            JSONArray json = new JSONArray(jsonStr);

            JsonToTelNumberConverter jsonToTelNumberConverter = new JsonToTelNumberConverter();
            List<TelNumber> result = jsonToTelNumberConverter.convert(json);

            TelNumbersToContentValuesConverter telNumbersToContentValuesConverter = new TelNumbersToContentValuesConverter();
            List<ContentValues> resultValues = telNumbersToContentValuesConverter.convert(result);

            // save into the storage
            ContentValues[] toStorage = new ContentValues[resultValues.size()];
            resultValues.toArray(toStorage);

            Log.d(App.TAG, "onProcessResponse for GetNumbersCommand: save data - " + toStorage.length);
            cr.bulkInsert(Contract.contentUri(Contract.TelNumbers.class), toStorage);

        } catch (JSONException e) {
            Log.e(App.TAG, "Failed to obtain json", e);
            onProcessError(Status.FAILED_TO_EXECUTE_REQUEST);
        } catch (IOException e) {
            Log.e(App.TAG, "Faield oto obtain response", e);
            onProcessError(Status.FAILED_TO_EXECUTE_REQUEST);
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        // no op
    }

    public static final Creator<GetNumbersCommand> CREATOR = new Creator<GetNumbersCommand>() {
        @Override
        public GetNumbersCommand createFromParcel(Parcel in) {
            return new GetNumbersCommand(in);
        }

        @Override
        public GetNumbersCommand[] newArray(int size) {
            return new GetNumbersCommand[size];
        }
    };

}
