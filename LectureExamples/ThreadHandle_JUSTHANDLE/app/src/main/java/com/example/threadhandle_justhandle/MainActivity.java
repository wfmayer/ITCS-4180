package com.example.threadhandle_justhandle;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    //Create Handler object here
    Handler handler;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating progress");
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);

        handler = new Handler(new Handler.Callback() {
            //This is where we handle the message
            @Override
            public boolean handleMessage(Message msg) {
//                Log.d("demo", "Message received ......" + msg.obj); // or msg.what

                switch (msg.what) {
                    case DoWork.STATUS_START:
                        progressDialog.setProgress(0);
                        progressDialog.show();
                        Log.d("demo", "Starting ....");
                        break;

                    case DoWork.STATUS_PROGRESS:
                        progressDialog.setProgress(msg.getData().getInt(DoWork.PROGRESS_KEY));
                        Log.d("demo", "Progress ...." + msg.getData().getInt(DoWork.PROGRESS_KEY));
                        break;

                    case DoWork.STATUS_STOP:
                        progressDialog.dismiss();
                        Log.d("demo", "Stopping ....");
                        break;
                }

                return false; //If false, had not been handled
                              // True would imply being done with handling
            }
        });

        new Thread(new DoWork()).start();

    }

    class DoWork implements Runnable {
        static final int STATUS_START = 0x00;
        static final int STATUS_PROGRESS = 0x01;
        static final int STATUS_STOP = 0x02;
        static final String PROGRESS_KEY = "PROGRESS";

        @Override
        public void run() { //THIS METHOD RUNS IN THE BACKGROUND THREAD!
            Message startMessage = new Message();
            startMessage.what = STATUS_START;// 'what' is a status parameter
            handler.sendMessage(startMessage);

            for (int i = 0; i<100; i++) {
                for (int j=0; j<100000000; j++) {
                }
                Message progressMessage = new Message();
                progressMessage.what = STATUS_PROGRESS;// 'what' is a status parameter
//                progressMessage.obj = (i + 1);
                Bundle bundle = new Bundle(); //Start bundle
                bundle.putInt(PROGRESS_KEY, (Integer) (i + 1));
                progressMessage.setData(bundle); // End bundle
                handler.sendMessage(progressMessage);
//                Message message = new Message();
//                message.what = i;// 'what' is a status parameter
//                message.obj = (Integer) i; // object
//                handler.sendMessage(message);
            }
            Message stopMessage = new Message();
            stopMessage.what = STATUS_STOP;// 'what' is a status parameter
            handler.sendMessage(stopMessage);
        }
    }
}
