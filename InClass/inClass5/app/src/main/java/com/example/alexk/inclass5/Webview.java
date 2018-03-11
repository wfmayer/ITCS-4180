package com.example.alexk.inclass5;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Webview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        //new loadWebViewAsync(webview).execute(url);

        WebView webview = new WebView(this);
        setContentView(webview);
        String url = getIntent().getStringExtra("StringName");
        Log.d("demo", url);

        webview.loadUrl(url);
    }


//    private class loadWebViewAsync extends AsyncTask<String, Integer, String> {
//
//        private ProgressDialog dialog = new ProgressDialog(Webview.this);
//        WebView webview;
//        public loadWebViewAsync(WebView paramView) {
//            super();
//            webview = paramView;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            this.dialog.setMessage("Loading URL...");
//            //this.dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            this.dialog.show();
//            setContentView(webview);
//            webview.setWebViewClient(new WebViewClient());
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String test = getIntent().getStringExtra("StringName");
//            Log.d("demo", "TESTING - " + test);
//            return strings[0];
//        }
//
//        @Override
//        protected void onPostExecute(String string) {
//            super.onPostExecute(string);
//            webview.loadUrl(string);
//            this.dialog.dismiss();
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//        }
//    }
}
