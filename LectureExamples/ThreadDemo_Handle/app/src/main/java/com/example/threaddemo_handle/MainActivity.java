package com.example.threaddemo_handle;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("dem", "onCreate (Main) thread id is " + Thread.currentThread().getId()); //Get id of thread

        new DoWorkAsync().execute(1000000);

//        new DoWorkAsync().execute("Bob", "Alice");
    }

    //Input, progress, output
    class DoWorkAsync extends AsyncTask<Integer, Integer, Double> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Updating Progress");
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.show();

            Log.d("dem", "onPreExecute thread id is " + Thread.currentThread().getId());

        }

        // This is what DoInBackground returns
        @Override
        protected void onPostExecute(Double aDouble) {
            Log.d("dem", "onPostExecute thread id is " + Thread.currentThread().getId());
            Log.d("dem", "onPostExecute value is " + aDouble );
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("dem", "onProgressUpdate thread id is " + Thread.currentThread().getId());
            Log.d("dem", "onProgressUpdate progress is " + values[0]);
            progressDialog.setProgress(values[0]);
        }

        @Override // this is the input, returns output
        //Only this method does not execute in the main thread
        protected Double doInBackground(Integer... params) { //Those 3 dots signify array

            Log.d("dem", "doInBackground thread id is " + Thread.currentThread().getId());
            double sum = 0;
            double count = 0;
            Random rand = new Random();

            for (int i = 0; i<100; i++) {
                for (int j=0; j<params[0]; j++) {
                    count++;
                    sum = rand.nextDouble() + sum;
                }
                publishProgress(i);
            }

            return sum/count;

//            for (String var: params) {
//                Log.d("dem", "Background params are " + var);
//            }
//
//            publishProgress(100);
//            return 333.33;
        }
    }
}

