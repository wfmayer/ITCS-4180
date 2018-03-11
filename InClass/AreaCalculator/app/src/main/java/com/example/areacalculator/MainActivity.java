package com.example.areacalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText leg1 = findViewById(R.id.editText_length1);
        final EditText leg2 = findViewById(R.id.editText_length2);

        final TextView description = findViewById(R.id.textView1);
        final TextView result = findViewById(R.id.textView2);

        final Button calc = findViewById(R.id.button_calc);
        final Button clear = findViewById(R.id.button_clear);

        final ImageButton triangle = findViewById(R.id.button_triangle);
        final ImageButton square = findViewById(R.id.button_square);
        final ImageButton circle = findViewById(R.id.button_circle);


        triangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText("Triangle");
//                leg1.setVisibility(View.VISIBLE);
                leg2.setVisibility(View.VISIBLE);
            }
        });

        square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText("Square");
                leg2.setVisibility(View.INVISIBLE);
            }
        });

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText("Circle");
                leg2.setVisibility(View.INVISIBLE);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leg1.setText("Length 1");
                leg2.setText("Length 2");
                description.setText("Choose a shape");
                result.setText("");
                leg1.setVisibility(View.VISIBLE);
                leg2.setVisibility(View.VISIBLE);
            }
        });

        leg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leg1.setText("");
            }
        });

        leg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leg2.setText("");
            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (description.getText().toString())
                {
                    case "Triangle":
                    {
                        int l1 = Integer.parseInt(leg1.getText().toString());
                        int l2 = Integer.parseInt(leg2.getText().toString());

                        double area = 0.5*l1*l2;
                        result.setText(String.valueOf(area));

                        break;
                    }
                    case "Square":
                    {
                        int l1 = Integer.parseInt(leg1.getText().toString());

                        double area = l1*l1;
                        result.setText(String.valueOf(area));

                        break;
                    }
                    case "Circle":
                    {
                        int l1 = Integer.parseInt(leg1.getText().toString());

                        double area = 3.1416*l1*l1;
                        result.setText(String.valueOf(area));
                        break;
                    }
                    default:
                    {
                        result.setText("ERROR");
                    }

                }
            }
        });
    }
}
