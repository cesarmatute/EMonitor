package com.cmsoft.e_monitor;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements DownloadEqsAsyncTAsk.DownloadEqsInterface {
    public static final String SELECTED_EARTHQUAKE = "selected_earthquake";
    private ListView earthquakeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        earthquakeListView = (ListView) findViewById(R.id.earthquake_listview);

        DownloadEqsAsyncTAsk downloadEqsAsyncTAsk = new DownloadEqsAsyncTAsk();
        downloadEqsAsyncTAsk.delegate = this;
        try {
            downloadEqsAsyncTAsk.execute(new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEqsDownloaded(ArrayList<Earthquake>eqList) {
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

