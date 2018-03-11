package com.example.alexk.photourlclass4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by AlexK on 2/17/2018.
 */

public class GetImageAsync extends AsyncTask<String, Void, Bitmap> {
    IPic iPic;
    Bitmap bitmap = null;
    ProgressDialog progressDialog;

    public GetImageAsync(IPic iPic) {
        this.iPic = iPic;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Log.d("demo", "Made to Image background...");
        HttpURLConnection connection = null;
        //Bitmap image = null; // Didn't use this, using a variable in the class is safer
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                publishProgress();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return bitmap;
    }


    @Override
    protected void onPostExecute(Bitmap aBitmap) {
        iPic.handlePic(aBitmap);
    }

    public static interface IPic{
        public void handlePic(Bitmap data); // could add more
    }
}
