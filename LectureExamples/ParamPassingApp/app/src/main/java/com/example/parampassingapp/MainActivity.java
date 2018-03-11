package com.example.parampassingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static String NAME_KEY = "NAME";
    static String AGE_KEY = "AGE";
    static String USER_KEY = "USER";
    static String PERSON_KEY = "PERSON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Main Activity");

        findViewById(R.id.button_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                intent.putExtra(PERSON_KEY, new Person("James Smith", 25.5, "Charlotte"));

//EXTRA STUFF
//                intent.putExtra(NAME_KEY, "Bob Smith");
//                intent.putExtra(AGE_KEY, (double) 25);
//
//                User user = new User("Alice", 25);
//                intent.putExtra(USER_KEY, user);
//
                  startActivity(intent);
            }
        });
    }
}
