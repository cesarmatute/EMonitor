package com.cmsoft.e_monitor;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements DownloadEqsAsyncTask.DownloadEqsInterface {
    public static final String SELECTED_EARTHQUAKE = "selected_earthquake";
    private ListView earthquakeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        earthquakeListView = (ListView) findViewById(R.id.earthquake_listview);

        if (Utils.isnetworkAvailable(this)) {
            downloadEarthquakes();
        } else {
            getEarthquakesFromDb();
        }


    }

    private void getEarthquakesFromDb() {
        EqDbHelper dbHelper = new EqDbHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query(EqContract.EqColumns.TABLE_NAME,null,null,null,null,null,null);

        ArrayList<Earthquake> eqList = new ArrayList<>();

        while (cursor.moveToNext()) {
            Long dateTime = cursor.getLong(EqContract.EqColumns.TIMESTAMP_COLUMN_INDEX);
            Double magnitude = cursor.getDouble(EqContract.EqColumns.MAGNITUDE_COLUMN_INDEX);
            String place = cursor.getString(EqContract.EqColumns.PLACE_COLUMN_INDEX);
            String longitude = cursor.getString(EqContract.EqColumns.LONGITUDE_COLUMN_INDEX);
            String latitude = cursor.getString(EqContract.EqColumns.LATITUDE_COLUMN_INDEX);

            eqList.add(new Earthquake(dateTime, magnitude, place, longitude, latitude));
        }

        cursor.close();
        fillEqList(eqList);
    }

    private void downloadEarthquakes() {
        DownloadEqsAsyncTask downloadEqsAsyncTAsk = new DownloadEqsAsyncTask(this);
        downloadEqsAsyncTAsk.delegate = this;
        try {
            downloadEqsAsyncTAsk.execute(new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEqsDownloaded(ArrayList<Earthquake>eqList) {
        fillEqList(eqList);
    }

    private void fillEqList(ArrayList<Earthquake>eqList) {
        final EqAdapter eqAdapter = new EqAdapter(this, R.layout.eq_list_item, eqList);
        earthquakeListView.setAdapter(eqAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Earthquake selectedEarthquake = eqAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(SELECTED_EARTHQUAKE, selectedEarthquake);
                startActivity(intent);
            }
        });
    }
}

