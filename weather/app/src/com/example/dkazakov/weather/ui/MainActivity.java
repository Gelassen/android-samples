package com.example.dkazakov.weather.ui;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import com.example.dkazakov.weather.R;
import com.example.dkazakov.weather.network.commands.GetWeatherCommand;
import com.example.dkazakov.weather.network.commands.SetUpDefaultValuesCommand;
import com.example.dkazakov.weather.storage.Contract;
import com.example.dkazakov.weather.ui.dialogs.AddCityDialog;
import com.example.dkazakov.weather.ui.dialogs.CityChooserDialog;
import com.example.dkazakov.weather.widget.AppWidgetProvider;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, CityChooserDialog.ChooseCityListener {

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment navigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence title;
    private long cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        title = getTitle();

        // Set up the drawer.
        navigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        new SetUpDefaultValuesCommand().start(this, null);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            handleWidgetCall(extras);
        }
        AppWidgetProvider.scheduleAlarms(this);
    }

    private void handleWidgetCall(Bundle extras) {
        if (extras.containsKey(AppWidgetManager.EXTRA_APPWIDGET_ID)) {
            final int appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            CityChooserDialog chooser = CityChooserDialog.newInstance(true, appWidgetId);
            chooser.show(getFragmentManager(), CityChooserDialog.TAG);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position, String city, long id) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance(city, id))
                .commit();
    }

    public void onSectionAttached(String city, long cityId) {
        this.title = city;
        this.cityId = cityId;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!navigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_update_weather:
                // update weather
                new GetWeatherCommand(cityId, GetWeatherCommand.DEFAULT_COUNT).start(this, null);
                break;

            case R.id.action_add_city:
                // show add fragment
                AddCityDialog dialog = AddCityDialog.newInstance("");
                dialog.show(getFragmentManager(), AddCityDialog.TAG);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChooseCity(int widgetId) {
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    @Override
    public void onCancel() {
        finish();
    }




    public static class WeatherListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

        private static final String ARG_CITY_NAME = "city";
        private static final String ARG_CITY_ID = "city_id";

        private WeatherAdapter adapter;


        public static WeatherListFragment newInstance(String city, long cityId) {
            WeatherListFragment fragment = new WeatherListFragment();
            Bundle args = new Bundle();
            args.putString(ARG_CITY_NAME, city);
            args.putLong(ARG_CITY_ID, cityId);
            fragment.setArguments(args);
            return fragment;
        }

        public WeatherListFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            adapter = new WeatherAdapter(getActivity(), R.layout.weather_item, null);
            ListView list = (ListView) rootView.findViewById(android.R.id.list);
            list.setAdapter(adapter);
            // make an update for this city
            Bundle args = getArguments();
            new GetWeatherCommand(
                    args.getLong(ARG_CITY_ID),
                    GetWeatherCommand.DEFAULT_COUNT
            ).start(getActivity(), null);
            getLoaderManager().restartLoader(0, null, this);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            Bundle args = getArguments();
            ((MainActivity) activity).onSectionAttached(
                    args.getString(ARG_CITY_NAME),
                    args.getLong(ARG_CITY_ID)
            );
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            final String selection = Contract.Weather.CITY_ID + "=?";
            final String[] selectionArgs = new String[] { Long.toString(getArguments().getLong(ARG_CITY_ID)) };
            return new CursorLoader(getActivity(), Contract.contentUri(Contract.Weather.class), null,
                    selection, selectionArgs,
                    Contract.Weather.DAY + " ASC");
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            adapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            adapter.swapCursor(null);
        }
    }

}
