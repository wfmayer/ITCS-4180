package com.example.otherintents;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.uncc.edu"));
        startActivity(intent);

//        Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:7046877476"));
//        startActivity(intent1);
    }
}
