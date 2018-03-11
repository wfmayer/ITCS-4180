package com.example.alexk.networkconnections;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()) {
                    Toast.makeText(MainActivity.this, "Internet Connection", Toast.LENGTH_SHORT).show();
                    new GetSimpleAsync().execute("http://api.theappsdr.com/simple.php");
                    //new GetImageAsync((ImageView) findViewById(R.id.imageView)).execute("https://cdn.pixabay.com/photo/2016/03/28/12/35/cat-1285634_1280.png");
//                    RequestParams params = new RequestParams();
//                    params.addJustKeyParameter("uncc");
//                    new GetDataParamsGETAsync(params).execute("http://api.theappsdr.com/params.php");
                    //new GetDataParamsPOSTAsync(params).execute("http://api.theappsdr.com/params.php");
                } else {
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Log.d("demo", "Network type = " + networkInfo.getType());

        if (networkInfo == null || !networkInfo.isConnected() //||
//                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
//                && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)
                ) {
            return false;
        }
        return true;
    }

    private class GetDataParamsPOSTAsync extends AsyncTask<String, Void, String> {

        RequestParams mParams;
        public GetDataParamsPOSTAsync (RequestParams params) {
            mParams = params;
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String result = null;
            try {
                URL url = new URL(mParams.getEncodedUrl(params[0]));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST"); //defaults on GET
                mParams.encodePostParameters(connection);
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); // Words, Character (String, Bytes)
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
//                    String apa = IOUtils.toString(connection.getInputStream(), "UTF8"); //Could return this
                    result = stringBuilder.toString();

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.d("demo", result);
            } else {
                Log.d("demo", "null result");
            }
        }
    }

    private class GetDataParamsGETAsync extends AsyncTask<String, Void, String> {

        RequestParams mParams;
        public GetDataParamsGETAsync (RequestParams params) {
            mParams = params;
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String result = null;
            try {
                URL url = new URL(mParams.getEncodedUrl(params[0]));
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); // Words, Character (String, Bytes)
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
//                    String apa = IOUtils.toString(connection.getInputStream(), "UTF8"); //Could return this
                    result = stringBuilder.toString();

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.d("demo", result);
            } else {
                Log.d("demo", "null result");
            }
        }
    }

    private class GetImageAsync extends AsyncTask<String, Void, Void> {
        ImageView imageView;
        Bitmap bitmap = null;

        public GetImageAsync(ImageView iv) {
            imageView = iv;
        }

        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection connection = null;
            //Bitmap image = null; // Didn't use this, using a variable in the class is safer
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (bitmap != null && imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private class GetSimpleAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String result = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); // Words, Character (String, Bytes)
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
//
//                    String apa = IOUtils.toString(connection.getInputStream(), "UTF8"); //Could return this

                    result = stringBuilder.toString();

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.d("demo", result);
            } else {
                Log.d("demo", "null result");
            }
        }
    }

}
