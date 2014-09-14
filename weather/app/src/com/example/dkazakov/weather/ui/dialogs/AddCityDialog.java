package com.example.dkazakov.weather.ui.dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.dkazakov.weather.R;
import com.example.dkazakov.weather.network.commands.GetCityCommand;


public class AddCityDialog extends DialogFragment {

    public static final String TAG = "TAG";

    private static final String ARG_CITY = "city";

    private EditText city;

    public static AddCityDialog newInstance(String city) {
        AddCityDialog fragment = new AddCityDialog();
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    public AddCityDialog(){
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.add_city_layout, null);
        city = (EditText) v.findViewById(R.id.add_city);
        city.setText(getArguments().getString(ARG_CITY));

        builder.setView(v);
        builder.setTitle(getString(R.string.dialog_add_city_title));
        builder.setMessage(getString(R.string.dialog_add_city_msg))
                .setPositiveButton(getString(R.string.dialog_add_city_positive), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final String search = city.getText().toString();
                        if (!TextUtils.isEmpty(search)) {
                            new GetCityCommand(city.getText().toString())
                                    .start(getActivity(), null);
                            CityChooserDialog.newInstance(false, 0).show(getFragmentManager(), CityChooserDialog.TAG);
                        } else {
                            // TODO show some message to user
                        }

                    }
                })
                .setNegativeButton(getString(R.string.dialog_add_city_negative), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        return builder.create();
    }

}
