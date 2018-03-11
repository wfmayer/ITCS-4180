package com.example.alexk.inclass5;

// In Class
//
//

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Source> sourceArrayList = new ArrayList<Source>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_list);


        if (isConnected()) {
            Toast.makeText(MainActivity.this, "Internet Connections", Toast.LENGTH_SHORT).show();
            new GetSourceAsync().execute("https://newsapi.org/v1/sources");
        } else {
            Toast.makeText(MainActivity.this, "No Connections", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Log.d("demo", "Network type = " + networkInfo.getType());

        if (networkInfo == null || !networkInfo.isConnected() //||
//                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
//                && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)
                ) {
            return false;
        }
        return true;
    }

    private class GetSourceAsync extends AsyncTask<String, Integer, ArrayList<Source>> {

        private ProgressDialog dialog = new ProgressDialog(MainActivity.this);
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
                    JSONArray sources = root.getJSONArray("sources");
                    for (int i = 0; i < sources.length(); i++) {
                        JSONObject sourceJson = sources.getJSONObject(i);
                        Source source = new Source();
                        source.setName(sourceJson.getString("name"));
                        source.setId(sourceJson.getString("id"));

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
        protected void onPreExecute() {
            this.dialog.setMessage("Loading Sources...");
            this.dialog.show();

        }

        @Override
        protected void onPostExecute(final ArrayList<Source> sources) {
            super.onPostExecute(sources);
            sourceArrayList = sources;
            listView = (ListView)findViewById(R.id.ListView_source);
            ArrayAdapter<Source> adapter =
                    new ArrayAdapter<Source>(MainActivity.this, android.R.layout.simple_list_item_1,
                            android.R.id.text1, sources);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("demo", sourceArrayList.toString());
                    Intent intent = new Intent(MainActivity.this, News.class);

                    intent.putExtra("StringName", sources.get(position).getId());
                    startActivity(intent);
                }
            });
            this.dialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}
