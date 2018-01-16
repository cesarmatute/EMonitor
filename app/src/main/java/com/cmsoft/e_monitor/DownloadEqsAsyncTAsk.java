package com.cmsoft.e_monitor;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by cesar on 03/01/18.
 */

public class DownloadEqsAsyncTAsk  extends AsyncTask<URL, Void, ArrayList<Earthquake>>{

    public DownloadEqsInterface delegate;

    public interface DownloadEqsInterface {
        void onEqsDownloaded(ArrayList<Earthquake> eqList);
    }

    @Override
    protected ArrayList<Earthquake> doInBackground(URL... urls) {
        String eqData;
        ArrayList<Earthquake> eqList = null;

        try {
            eqData = downloadData(urls[0]);
            eqList = parseDataFromJson(eqData);
        } catch (IOException e){
            e.printStackTrace();
        }

        return eqList;
    }

    @Override
    protected void onPostExecute(ArrayList<Earthquake> eqList) {
        super.onPostExecute(eqList);
        delegate.onEqsDownloaded(eqList);
    }

    private ArrayList<Earthquake> parseDataFromJson(String eqsData) {
        ArrayList<Earthquake> eqList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(eqsData);
            JSONArray featuresJsonArray = jsonObject.getJSONArray("features");

            for (int i=0; i<featuresJsonArray.length();i++) {
                JSONObject featuresJsonObject = featuresJsonArray.getJSONObject(i);
                JSONObject propertiesJsonObject = featuresJsonObject.getJSONObject("properties");
                double magnitude = propertiesJsonObject.getDouble("mag");
                String place = propertiesJsonObject.getString("place");
                Long timeStamp = propertiesJsonObject.getLong("time");
                JSONObject geometryJsonObject = featuresJsonObject.getJSONObject("geometry");
                JSONArray coordinatesJsonObject = geometryJsonObject.getJSONArray("coordinates");
                String longitude = coordinatesJsonObject.getString(0);
                String laitude = coordinatesJsonObject.getString(0);
                eqList.add(new Earthquake(timeStamp, magnitude, place, longitude, laitude));
                Log.d("JSON", "Magnitude: " + magnitude + " Place: " + place);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return eqList;
    }

    private String downloadData(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);

        } catch (IOException e) {

        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }

            if(inputStream != null) {
                inputStream.close();
            }

        }

        return jsonResponse;
    }

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream!=null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader= new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }
}
