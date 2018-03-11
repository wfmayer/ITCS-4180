package com.example.alexk.group3_inclass06;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by AlexK on 2/26/2018.
 */

public class GetAPIAsync extends AsyncTask<String, Integer, ArrayList<Source>> {

    IData iData;
    ArrayList<Source> sourceArrayList;


    public GetAPIAsync(IData iData) { this.iData = iData; }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Source> doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Source> result = new ArrayList<>();
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); // Words, Character (String, Bytes)
                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                String json = stringBuilder.toString();
                JSONObject root = new JSONObject(json);
                JSONArray sources = root.getJSONArray("articles");
                for (int i = 0; i < sources.length(); i++) {
                    JSONObject sourceJson = sources.getJSONObject(i);
                    Source source = new Source();

                    source.setTitle(sourceJson.getString("title"));
                    source.setDescription(sourceJson.getString("description"));
                    source.setImage(sourceJson.getString("urlToImage"));
                    source.setPublishedDate(sourceJson.getString("publishedAt"));

                    result.add(source);
                    publishProgress(i);
                }
            }
        } catch (Exception e) {
            //Handle Exceptions
        } finally {
            //Close the connections
        }
        Log.d("demo", "toString - " + result.toString());
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<Source> sources) {
        super.onPostExecute(sources);
        //sourceArrayList = sources;
        Log.d("demo", sources.toString());
        iData.handleData(sources);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    public static interface IData {
        public void handleData(ArrayList<Source> data);
    }
}
