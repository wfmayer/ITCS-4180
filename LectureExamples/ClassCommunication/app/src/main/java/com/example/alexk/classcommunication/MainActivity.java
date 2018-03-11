package com.example.alexk.classcommunication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements GetTweetsAsyncTask.IData {

    LinkedList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetTweetsAsyncTask(MainActivity.this).execute("");
            }
        });
    }

    public void handleData(LinkedList<String> strings) {
        this.data = strings;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Tweets")
                .setItems(data.toArray(new CharSequence[data.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//    public void handleData() {
//        this.data = data;
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setTitle("Tweets")
//                .setItems(data.toArray(new CharSequence[data.size()]), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//        final AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
}
