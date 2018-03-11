package com.example.alertdialogdemo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.media.tv.TvContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    CharSequence[] items = {"Red", "Blue", "Green", "Yellow"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        findViewById(R.id.button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        //Making the builder
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setTitle("Pick a color.")
//
//Checked boxes
//                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                        Log.d("Demo", items[which] + " is " + isChecked);
//                    }
//                })
//No List choice
//                .setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Log.d("demo", "Selected " + items[which]);
//                    }
//                })
//Radio Choice
//                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Log.d("demo", "Selected " + items[which]);
//                    }
//
//                })

        //Setting positive button
//            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Log.d("Demo", "Selected Okay");
//            }
//        })
//            .setCancelable(false);

//        .setMessage("Are you sure?") Non-List
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() { Non-List
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Log.d("demo", "Clicked Ok");
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Log.d("demo", "Clicked Cancel");
//                    }
//                });

        //Using the builder
//        final AlertDialog alert = builder.create();
//        To utilize this, set an on click and use alert.show();

    }
}