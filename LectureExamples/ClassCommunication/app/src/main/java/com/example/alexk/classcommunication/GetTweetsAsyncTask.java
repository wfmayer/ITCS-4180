package com.example.alexk.classcommunication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

import java.util.LinkedList;

/**
 * Created by AlexK on 2/16/2018.
 */

public class GetTweetsAsyncTask extends AsyncTask<String, Void, LinkedList<String>> {

//    MainActivity activity; //Naive method.
//    public GetTweetsAsyncTask(MainActivity activity) {
//        this.activity = activity;
//    }

    IData iData;
    public GetTweetsAsyncTask(IData iData) {this.iData = iData;}

    @Override
    protected LinkedList<String> doInBackground(String... strings) {
        LinkedList<String> tweets = new LinkedList<>();
        for (int i = 0; i<10; i++){
            tweets.add("Tweets " + i);
        }

        for (int i = 0; i<1000; i++){
            for (int j = 0; j<100; j++) {
            }
        }

        return tweets;
    }

    @Override
    protected void onPostExecute(LinkedList<String> strings) {
        //activity.handleData(strings);
        iData.handleData(strings);
    }

    public static interface IData{
        public void handleData(LinkedList<String> data); // could add more
    }

}
