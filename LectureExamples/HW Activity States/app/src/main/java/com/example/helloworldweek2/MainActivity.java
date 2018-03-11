package com.example.helloworldweek2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("demo", "Inside onCreate");
    }

    public MainActivity() {
        super();
    }

    @Override
    protected void onStart() {
        Log.d("demo", "Inside onStart");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.d("demo", "Inside onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("demo", "Inside onResume");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d("demo", "Inside onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("demo", "Inside onDestroy");
        super.onDestroy();
    }
}
