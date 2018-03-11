package com.example.alexk.photourlclass4;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements GetDataParamsGETAsync.IData, GetImageAsync.IPic {

    LinkedList<String> data;
    Iterator<String> iterator;
    Bitmap passedBit;
//    ImageView imageView;


    public static ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        final EditText editText = findViewById(R.id.editText);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()) {
                    Toast.makeText(MainActivity.this, "Internet Connection", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.imageButton).setVisibility(View.VISIBLE);
                    findViewById(R.id.imageButton2).setVisibility(View.VISIBLE);
                    String editTextContents = editText.getText().toString();
                    RequestParams params = new RequestParams();
                    params.addParameter("keyword", editTextContents);
                    new GetDataParamsGETAsync(params, MainActivity.this).execute(" http://dev.theappsdr.com/apis/photos/index.php");
                    //new GetImageAsync(imageView).execute(data.peek());

                } else {
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (passedBit != null) {
            imageView.setImageBitmap(passedBit);
        }
    }

    @Override
    public void handleData(LinkedList<String> data) {
        this.data = data;
        iterator = this.data.iterator();
        Log.d("demo", "Made it to HandleData...");
        Log.d("demo", "Before image Async... " + data.peek());
        new GetImageAsync(MainActivity.this).execute(data.peek());

    }

    @Override
    public void handlePic(Bitmap data) {
        if (data != null) {
            Log.d("demo", "Made it to handle pic...");
            passedBit = data;
            Log.d("demo", passedBit.toString());
            MainActivity.imageView.setImageBitmap(passedBit);
        }
    }

    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Log.d("demo", "Network type = " + networkInfo.getType());
        if (networkInfo == null || !networkInfo.isConnected()) {return false;}
        return true;
    }
}
