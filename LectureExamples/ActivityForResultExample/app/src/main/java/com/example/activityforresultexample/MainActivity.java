package com.example.activityforresultexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final int LE_CODE = 100;
    public static final String VALUE_KEY = "value";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_goto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivityForResult(intent, LE_CODE);
            }
        });
    }

    //Request code identifies request, this aids in correctly receiving response
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LE_CODE) {
            if (resultCode == RESULT_OK) {
                String value = data.getExtras().getString(VALUE_KEY);
                Log.d("demo", value);
            } else if (resultCode == RESULT_CANCELED) {
                    Log.d("demo", "No value");
            }
        }

    }
}
