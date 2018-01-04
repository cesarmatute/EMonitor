package com.cmsoft.e_monitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView earthquakeListView = (ListView) findViewById(R.id.earthquake_listview);
        ArrayList<Earthquake> eqList = new ArrayList<>();

        eqList.add(new Earthquake("4.6","97 Km S of Wonosari, Indonesia"));
        eqList.add(new Earthquake("2.3","16 Km S of Joshua Tree, CA"));
        eqList.add(new Earthquake("3.4","97 Km S of Wonosari, Indonesia"));

        EqAdapter eqAdapter = new EqAdapter(this, R.layout.eq_list_item, eqList);
        earthquakeListView.setAdapter(eqAdapter);

        DownloadEqsAsyncTAsk downloadEqsAsyncTAsk = new DownloadEqsAsyncTAsk();
        try {
            downloadEqsAsyncTAsk.execute(new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }


}

