package com.example.dkazakov.weather.ui.dialogs;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.dkazakov.weather.R;
import com.example.dkazakov.weather.network.commands.GetCityCommand;
import com.example.dkazakov.weather.storage.Contract;
import com.example.dkazakov.weather.storage.PreferenceHelper;
import com.example.dkazakov.weather.ui.CityAdapter;
import com.example.dkazakov.weather.widget.AppWidgetProvider;

public class CityChooserDialog extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "CHOOSE_CITY_DIALOG_TAG";

    private static final String KEY_WIDGET = "KEY_WIDGET";
    private static final String KEY_WIDGET_ID = "KEY_WIDGET_ID";

    private CityAdapter adapter;
    private ChooseCityListener listener;

    public static CityChooserDialog newInstance(boolean widgetLaunch, int widgetId) {
        CityChooserDialog fragment = new CityChooserDialog();
        Bundle args = new Bundle(2);
        args.putBoolean(KEY_WIDGET, widgetLaunch);
        args.putInt(KEY_WIDGET_ID, widgetId);
        fragment.setArguments(args);
        return fragment;
    }

    public CityChooserDialog(){
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ChooseCityListener) {
            listener = (ChooseCityListener) activity;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle args = getArguments();
        final boolean forWidget = args.getBoolean(KEY_WIDGET);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.choose_city_layout, null);

        final ListView list = (ListView) v.findViewById(android.R.id.list);
        adapter = new CityAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, null);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (forWidget) {
                    // update remote view
                    final int appWidgetId = args.getInt(KEY_WIDGET_ID);
                    addWidgetInfo(view, appWidgetId);
                    if (listener != null) {
                        listener.onChooseCity(appWidgetId);
                    }
                } else {
                    addNewCity(view);
                }
            }
        });

        builder.setView(v);
        builder.setTitle(getString(R.string.dialog_choose_city_title));
        builder.setNegativeButton(getString(R.string.dialog_choose_city_negative), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dismiss();
                if (forWidget) {
                    if (listener != null) {
                        listener.onCancel();
                    }
                }

            }
        });
        getLoaderManager().initLoader(0, null, this);
        return builder.create();
    }

    private void addWidgetInfo(View view, int widgetId){
        CityAdapter.ViewHolder holder = (CityAdapter.ViewHolder) view.getTag();
        PreferenceHelper.setCityIdByWidgetId(getActivity(), widgetId, holder.getId());
        AppWidgetProvider.updateWidgets(getActivity());
    }

    private void addNewCity(View view) {
        CityAdapter.ViewHolder holder = (CityAdapter.ViewHolder) view.getTag();

        final ContentValues newCity = new ContentValues(5);
        newCity.put(Contract.Cities.ID, holder.getId());
        newCity.put(Contract.Cities.CITY, holder.getCity());
        newCity.put(Contract.Cities.COUNTRY, holder.getCountry());
        newCity.put(Contract.Cities.LAT, holder.getLat());
        newCity.put(Contract.Cities.LONG, holder.getLon());

        Uri cityUri = Contract.contentUri(Contract.Cities.class);
        getActivity().getContentResolver().insert(cityUri, newCity);
        // clear search table
        getActivity().getContentResolver().delete(Contract.contentUri(Contract.CitiesForChoose.class), null, null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final boolean widget = getArguments().getBoolean(KEY_WIDGET);
        final Uri cities = Contract.contentUri(widget ? Contract.Cities.class : Contract.CitiesForChoose.class);
        return new CursorLoader(getActivity(), cities,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    public interface ChooseCityListener {
        public void onChooseCity(int widgetId);
        public void onCancel();
    }
}
