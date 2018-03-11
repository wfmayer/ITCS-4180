package com.example.hw01_group3;

//COMMENTS FOR GRADE//
// Homework Assignment #1
// File Name: HW01_Group3
// Names: Alex Kennedy, Will Mayer

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    //Keys/Codes/Values
    public static final int CREATE_REQ_CODE = 666;
    public static final String VALUE_KEY = "value";
    public static final String EDIT_STRING = "edit";
    public static final String DELETE_STRING = "delete";
    public static final String DISPLAY_STRING = "display";

    //Thread
    ExecutorService threadPool;

    public static Bitmap ByteArrayToBitmap(byte[] byteArray)
    {
        Bitmap bitmap_intent = BitmapFactory.decodeByteArray(byteArray, 0,
                byteArray.length);
        return bitmap_intent;
    }

    public static ArrayList<Contact> contactArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (contactArrayList == null) {
            contactArrayList = new ArrayList<>();
        }

        threadPool = Executors.newFixedThreadPool(2);

        //Create New button//
        findViewById(R.id.button_createNew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateContact.class);
                startActivityForResult(intent, CREATE_REQ_CODE);
            }
        });

        //Edit Contact Button
        findViewById(R.id.button_editContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactsList.class);
                intent.putExtra("StringName", EDIT_STRING);
                startActivity(intent);
            }
        });

        //Delete Contact Button
        findViewById(R.id.button_deleteContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactsList.class);
                intent.putExtra("StringName", DELETE_STRING);
                startActivity(intent);
            }
        });

        //Display Contact Button
        findViewById(R.id.button_displayContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactsList.class);
                intent.putExtra("StringName", DISPLAY_STRING);
                startActivity(intent);
            }
        });

        //Finish Button
        findViewById(R.id.button_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                Contact contact = data.getParcelableExtra(VALUE_KEY);
                new DoWorkAsyncAdd().execute(contact);

            } else if (resultCode == RESULT_CANCELED) {
                Log.d("demo", "No value");
            }
        }
    }


    class DoWorkAsyncAdd extends AsyncTask<Contact, Integer, ArrayList<Contact>> {

        @Override
        protected ArrayList<Contact> doInBackground(Contact... contacts) {
            contactArrayList.add(contacts[0]);
            return null;
        }
    }

}