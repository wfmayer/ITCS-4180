package com.example.helloapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity { //implements View.OnClickListener

    private RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_radio); //this is where you pick your preferred layout

        rg = findViewById(R.id.radioGroup1);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = findViewById(checkedId);
                Log.d("demo", "Checked the " + rb.getText().toString());
            }
        });

        findViewById(R.id.buttonChecking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rg.getCheckedRadioButtonId() == R.id.radio1) {
                    Log.d("demo", "Checked is radio 1");
                } else if (rg.getCheckedRadioButtonId() == R.id.radio2) {
                    Log.d("demo", "Checked is radio 2");
                } else if (rg.getCheckedRadioButtonId() == R.id.radio3) {
                    Log.d("demo", "Checked is radio 3");
                } else if (rg.getCheckedRadioButtonId() == -1) {
                    Log.d("demo", "None are checked");
                }
            }
        });

//        Button btn = findViewById(R.id.buttonOK);
//        btn.setOnClickListener(new View.OnClickListener() { // set OnClickListener and implemented an Interface
//            @Override
//            public void onClick(View v) {
//                Log.d("demo", "OK button clicked!");
//            }
//        });
//
//        findViewById(R.id.buttonCancel).setOnClickListener(this); //this referring to method declared outside the main
//        findViewById(R.id.buttonOtherCancel).setOnClickListener(this);
//
////        Button btn = (Button) findViewById(R.id.buttonCancel); //CAST!! Casting view as a button
////
////        Log.d("demo", "Button text is " + btn.getText().toString());
//
//
////        Log.d("demo", "Hello Worldddd");
////        Log.w("demo", "Hello Worldddd");
////
////        String s = getResources().getString(R.string.are_you_sure);
////        Log.d("demo", s);
////
////        String[] colors = getResources().getStringArray(R.array.colors_array);
////        for (String str: colors) {
////            Log.d("demo", str);
////        }
//
//        //NOTE: Think of R as a resource file, as in this case
//        // you are accessing (and changing the name) of the activity variable
//        // R.layout.activity_main_new;
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        if(v.getId() == (R.id.buttonOtherCancel)) {
//            Log.d("demo", "OTHER cancel button clicked!");
//        } else
//
//        Log.d("demo", "Cancel button clicked!");
//    }
//
//
//    public void SureButtonClicked(View view) {
//        Log.d("demo", "Sure button clicked!");
    }
}
