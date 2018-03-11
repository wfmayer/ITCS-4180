package com.example.threaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ExecutorService threadPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        threadPool = Executors.newFixedThreadPool(4);

        findViewById(R.id.button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BAD IDEA, USER WILL OVERCLOCK WORKER THREADS
//                USE A THREAD POOL
//                Thread thread = new Thread(new DoWork(), "Worker 1!!!!!!!!");
//                thread.start();
                threadPool.execute(new DoWork());
            }
        });
    }

    class DoWork implements Runnable {

        @Override
        public void run() { //THIS METHOD RUNS IN THE BACKGROUND THREAD!
            Log.d("demo", "Started work before loop!");
            for (int i = 0; i<10000; i++) {
                for (int j=0; j<100000; j++) {
                }
            }
            Log.d("demo", "Ended the work!");
        }
    }
}
