package com.example.alexk.photourlclass4;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by AlexK on 2/17/2018.
 */

class GetDataParamsGETAsync extends AsyncTask<String, Void, LinkedList<String>> {

    RequestParams mParams;
    IData iData;

    public GetDataParamsGETAsync(RequestParams params, IData iData) {
        mParams = params;
        this.iData = iData;
    }

    @Override
    protected LinkedList<String> doInBackground(String... params) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String result = null;
        LinkedList<String> linkedList = new LinkedList<>();
        try {
            URL url = new URL(mParams.getEncodedUrl(params[0]));
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); // Words, Character (String, Bytes)
                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }


                result = stringBuilder.toString();
                Log.d("demo", result);
                for (String item: result.split("\n")) {
                    linkedList.add(item);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return linkedList;
    }

    @Override
    protected void onPostExecute(LinkedList<String> strings) {
//        super.onPostExecute(strings);
        Log.d("demo", strings.peek());
        Log.d("demo", strings.getLast());
        iData.handleData(strings);
    }

    public static interface IData{
        public void handleData(LinkedList<String> data); // could add more
    }
}