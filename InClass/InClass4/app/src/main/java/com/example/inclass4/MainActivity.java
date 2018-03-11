package com.example.inclass4;

// Assignment #3
// Alex Kennedy
// William Mayer



import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    public static String[] createArray(Bundle bundle, int count) {

        Log.d("demo", "Inside the Method");
        String[] tempArr = new String[count];
        Log.d("demo", "Getting the string array...");
//        bundle.getStringArray(DoWork.PROGRESS_KEY);
        Log.d("demo", "CREATEARRAY BEFORE loop");

        for (int i = 0; i<count; i++) {
            Log.d("demo", "CREATEARRAY INSIDE loop");
            tempArr[i] = bundle.getStringArray(DoWork.PROGRESS_KEY)[i];
        }

        return tempArr;
    };


    ExecutorService threadPool;
    Handler handler;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);


        threadPool = Executors.newFixedThreadPool(2);

//        String[] passwordArr = null;

        final TextView textView_countDynamic = findViewById(R.id.textView_countDynamic);
        final TextView textView_lengthDynamic = findViewById(R.id.textView_lengthDynamic);
        final TextView textView_passDynamic = findViewById(R.id.textView_passDynamic);


        final SeekBar seekBar_count = findViewById(R.id.seekBar_count);
        seekBar_count.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String pro = Integer.toString(progress);
                textView_countDynamic.setText(pro);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        final SeekBar seekBar_length = findViewById(R.id.seekBar_length);
        seekBar_length.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String pro = Integer.toString(progress);
                textView_lengthDynamic.setText(pro);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        findViewById(R.id.button_Async).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (seekBar_count.getProgress() < 1 || seekBar_length.getProgress() < 8) {
                        throw new InputMismatchException();
                    } else {
                        new DoWorkAsync().execute(seekBar_count.getProgress(), seekBar_length.getProgress());
                    }
                } catch (InputMismatchException e) {
                    if (seekBar_count.getProgress() < 1 && seekBar_length.getProgress() < 8) {
                        Toast.makeText(MainActivity.this, "Count minimum: 1 | Length minimum: 8", Toast.LENGTH_LONG).show();
                    } else if (seekBar_count.getProgress() < 1) {
                        Toast.makeText(MainActivity.this, "Count minimum: 1", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Length minimum: 8", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        findViewById(R.id.button_Thread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setMax(seekBar_count.getProgress());

                try {

                    if (seekBar_count.getProgress() < 1 || seekBar_length.getProgress() < 8) {
                        throw new InputMismatchException();
                    } else
                    handler = new Handler(new Handler.Callback() {
                        @Override
                        public boolean handleMessage(Message msg) {
                            switch (msg.what) {
                                case DoWork.STATUS_START:
                                    Log.d("demo", "Made it to startCase");
                                    progressBar.setProgress(0);
                                    break;

                                case DoWork.STATUS_PROGRESS:
                                    Log.d("demo", "Made it to progressCase");
                                    progressBar.setProgress((int) msg.obj);
                                    Log.d("demo", "OBJECT IS " + msg.obj);

                                    break;

                                case DoWork.STATUS_STOP:
                                    Log.d("demo", "Made it to stopCase");
                                    progressBar.setProgress(0);
//                                    Bundle bundle = msg.getData().getBundle(DoWork.PROGRESS_KEY);
//                                    Log.d("demo", "STOP based data = " + bundle.getStringArray(DoWork.PROGRESS_KEY));
                                    break;

                                case DoWork.STATUS_SOMETHING:

//                                   Bundle bundle = msg.getData().getBundle(DoWork.PROGRESS_KEY);
                                    Bundle bundle = msg.getData();

                                    Log.d("demo", "msgData STATUS_SOMETHING = " + msg.getData().getStringArray(DoWork.PROGRESS_KEY)[1]);
                                    Log.d("demo", "About to invoke createArray...");
                                    final String[] passwordArr = createArray(bundle, seekBar_count.getProgress());
                                     Log.d("demo", "FLAGGGGGGGGGG");
                                     Log.d("demo", "This is the PASSWORD ARRAY " + Arrays.toString(passwordArr));

                                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setTitle("Passwords")
                                            .setItems(passwordArr, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    textView_passDynamic.setText(passwordArr[which]);
                                                }
                                            }).setCancelable(false);

                                    final AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    break;
                            }

                            return false;
                        }
                    });
                    threadPool.execute((new DoWork(seekBar_count.getProgress(), seekBar_length.getProgress())));
                } catch (InputMismatchException e) {
                    if (seekBar_count.getProgress() < 1 && seekBar_length.getProgress() < 8) {
                        Toast.makeText(MainActivity.this, "Count minimum: 1 | Length minimum: 8", Toast.LENGTH_LONG).show();
                    } else if (seekBar_count.getProgress() < 1) {
                        Toast.makeText(MainActivity.this, "Count minimum: 1", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Length minimum: 8", Toast.LENGTH_LONG).show();
                    }

                }


            }
        });

    }
    String[] strArr;
//    Bundle b = new Bundle(count);

    class DoWork implements Runnable {

        static final int STATUS_START = 0x00;
        static final int STATUS_PROGRESS = 0x01;
        static final int STATUS_STOP = 0x02;
        static final String PROGRESS_KEY = "PROGRESS";
        static final int STATUS_SOMETHING = 0x03;

        int count;
        int length;

        DoWork(int co, int ln) {
            this.count = co;
            this.length = ln;
        }


        @Override
        public void run() { //THIS METHOD RUNS IN THE BACKGROUND THREAD!]

            Bundle stringBundle = new Bundle();
            String[] stringArr = new String[count];
            strArr = new String[count];

            //SENDING THE START MESSAGE
            Message startMessage = new Message();
            startMessage.what = STATUS_START;// 'what' is a status parameter
            handler.sendMessage(startMessage);

            //DOING THE WORK
            for (int i = 0; i<count; i++) {
                stringArr[i] = Util.getPassword(length);
                Log.d("demo", "Here's the StringArr -> " + Arrays.asList(stringArr));

                //SENDING PROGRESS BAR RELATED MESSAGE
                Message progressMessage = new Message();
                progressMessage.what = STATUS_PROGRESS;
                progressMessage.obj = (i + 1);
                progressMessage.setData(stringBundle);
                handler.sendMessage(progressMessage);
            }
            stringBundle.putStringArray(PROGRESS_KEY, stringArr);
            Log.d("demo", "Here is the string bundle -> " + stringBundle);

            //PACKAGING THE STRING ARRAY
            Message messageArray = new Message();
            messageArray.what = STATUS_SOMETHING;
            messageArray.setData(stringBundle);
            handler.sendMessage(messageArray);

            //STOPING THE WORK
            Message stopMessage = new Message();
            stopMessage.what = STATUS_STOP;// 'what' is a status parameter
            stopMessage.setData(stringBundle);
            handler.sendMessage(stopMessage);
        }
    }

    class DoWorkAsync extends AsyncTask<Integer, Integer, Double> {

        ProgressBar progressBar = findViewById(R.id.progressBar);

        public void setProgressBar(ProgressBar bar) {
            this.progressBar = bar;
        }

        TextView textView_passDynamic = findViewById(R.id.textView_passDynamic);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            strArr = null;
            progressBar.setProgress(0);
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Passwords")
                    .setItems(strArr, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            textView_passDynamic.setText(strArr[which]);
                        }
                    }).setCancelable(false);

            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            progressBar.setProgress(0);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("demo", "Here it is..." + values[0]);
            if (this.progressBar != null) {
                progressBar.setProgress(values[0]);
            }
        }

        @Override
        protected Double doInBackground(Integer... integers) {

            int count = integers[0];
            int length = integers[1];

            strArr = new String[count];
            for (int i = 0; i<count; i++) {
                strArr[i] = Util.getPassword(length);
                publishProgress(i);
            }

            return null;
        }
    }
}
