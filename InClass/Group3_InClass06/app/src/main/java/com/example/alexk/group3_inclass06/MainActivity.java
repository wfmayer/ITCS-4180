package com.example.alexk.group3_inclass06;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetAPIAsync.IData{

    ArrayList<Source> sourceArrayList;
    ListView listView;
    CharSequence[] items = {"Business", "Entertainment", "General", "Health",
    "Science", "Sports", "Technology"};
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);

        final TextView textView_category = findViewById(R.id.textView_category);

        if (isConnected()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Select from the list").setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=668fbb2cec014aa3b0126a19fe6955e3&category=" + items[which].toString();
                    for (int i = 1; i <= 50; i++) {
                        progressBar.setProgress(i);
                    }
                    new GetAPIAsync(MainActivity.this).execute(url);
                    textView_category.setText(items[which].toString());
                    Log.d("demo", url);
                }
            });
            final AlertDialog alertDialog = builder.create();
            findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setProgress(0);
                    alertDialog.show();
                }
            });
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


    @Override
    public void handleData(ArrayList<Source> data) {
        sourceArrayList = new ArrayList<>(data);
        if (data.size() == 0) {
            Toast.makeText(MainActivity.this, "No News Found", Toast.LENGTH_SHORT).show();
        } else {
        listView = (ListView) findViewById(R.id.listView);
        SourceAdapter sourceAdapter = new SourceAdapter(MainActivity.this, R.layout.source_item_layout, sourceArrayList);
        listView.setAdapter(sourceAdapter);
        for (int i = 1; i <= 100; i++) {
            progressBar.setProgress(i);
        }
        }
    }
}
