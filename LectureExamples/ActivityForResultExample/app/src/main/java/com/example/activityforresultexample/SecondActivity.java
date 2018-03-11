package com.example.activityforresultexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        editText = findViewById(R.id.editText);

        findViewById(R.id.button_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = editText.getText().toString();
                if (value.length() == 0) {
                    Log.d("demo", "TEST");
                    setResult(RESULT_CANCELED);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.VALUE_KEY, value);
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });
    }
}
