package com.example.dkazakov.weather.ui.dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.dkazakov.weather.R;
import com.example.dkazakov.weather.network.commands.GetCityCommand;
import com.example.dkazakov.weather.network.model.City;
import com.example.dkazakov.weather.storage.Contract;


public class DeleteCityDialog extends DialogFragment {

    private static final String ARG_CITY = "city";
    private static final String ARG_CITY_ID = "city_id";
    public static final String TAG = "DELETE_CITY_TAG";

    public static DeleteCityDialog newInstance(String city, long cityId) {
        DeleteCityDialog fragment = new DeleteCityDialog();
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city);
        args.putLong(ARG_CITY_ID, cityId);
        fragment.setArguments(args);
        return fragment;
    }

    public DeleteCityDialog(){
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String msg = String.format(
                getString(R.string.dialog_delete_city_msg), getArguments().getString(ARG_CITY)
        );
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.dialog_delete_city_title));
        builder.setMessage(msg)
                .setPositiveButton(getString(R.string.dialog_delete_city_positive), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final long cityId = getArguments().getLong(ARG_CITY_ID);
                        Uri cityUri = Contract.contentUri(Contract.Cities.class);
                        String selection = Contract.Cities.ID + "=?";
                        String[] selectionArgs = new String[] { Long.toString(cityId) };
                        getActivity().getContentResolver().delete(
                                cityUri, selection, selectionArgs
                        );
                    }
                })
                .setNegativeButton(getString(R.string.dialog_delete_city_negative), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        return builder.create();
    }

}
