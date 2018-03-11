package com.example.alexk.jsonparsing;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnected()) {
                    Toast.makeText(MainActivity.this, "Internet Connections", Toast.LENGTH_SHORT).show();
                    new GetSimpleAsync().execute("http://api.theappsdr.com/json/");
                } else {
                    Toast.makeText(MainActivity.this, "No Connections", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    private class GetSimpleAsync extends AsyncTask<String, Void, ArrayList<Person>> {
        @Override
        protected ArrayList<Person> doInBackground(String... params) {
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            ArrayList<Person> result = new ArrayList<>();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); // Words, Character (String, Bytes)
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    String json  = stringBuilder.toString();

                    JSONObject root = new JSONObject(json);
                    JSONArray persons = root.getJSONArray("persons");
                    for (int i = 0; i < persons.length(); i++) {
                        JSONObject personJson = persons.getJSONObject(i);
                        Person person = new Person();
                        person.name = personJson.getString("name");
                        person.id = personJson.getLong("id");
                        person.age = personJson.getInt("age");

                        JSONObject addressJson = personJson.getJSONObject("address");

                        Address address = new Address();
                        address.line1 = addressJson.getString("line1");
                        address.city = addressJson.getString("city");
                        address.state = addressJson.getString("state");
                        address.zip = addressJson.getString("zip");
                        person.address = address;

                        result.add(person);

                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Person> result) {
            if (result.size() > 0) {
                Log.d("demo", result.toString());
            } else {
                Log.d("demo", "null result");
            }
        }
    }

}
