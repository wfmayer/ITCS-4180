package com.example.alexk.inclass5;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class News extends AppCompatActivity {

    ListView listView;

    ArrayList<Article> articleArrayList = new ArrayList<Article>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        String theID = getIntent().getStringExtra("StringName");
        Log.d("demo", theID);

        new GetArticleAsync().execute("https://newsapi.org/v1/articles?source=" + theID + "&apiKey=a6a87c91dcfe4eb2ad221b8fa0e55fed");

        listView = findViewById(R.id.listView_articles);


    }

    private class GetArticleAsync extends AsyncTask<String, Integer, ArrayList<Article>> {
        private ProgressDialog dialog = new ProgressDialog(News.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog.setMessage("Loading Articles...");
            this.dialog.show();
        }


        @Override
        protected ArrayList<Article> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            StringBuilder stringBuilder = new StringBuilder();
            ArrayList<Article> result = new ArrayList<>();
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); // Words, Character (String, Bytes)
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }

                    String json = stringBuilder.toString();
                    JSONObject root = new JSONObject(json);
                    JSONArray articles = root.getJSONArray("articles");
                    for (int i = 0; i < articles.length(); i++) {
                        JSONObject sourceJson = articles.getJSONObject(i);
                        Article article = new Article();
                        article.setAuthor(sourceJson.getString("author"));
                        article.setTitle(sourceJson.getString("title"));
                        article.setUrl(sourceJson.getString("url"));
                        article.setUrlToimage(sourceJson.getString("urlToImage"));
                        article.setPublishedAt(sourceJson.getString("publishedAt"));

                        publishProgress(i);
                        result.add(article);
                    }
                }
            } catch (Exception e) {
                //Handle Exceptions
            } finally {
                //Close the connections
            }
            Log.d("demo", "toString - " + result.toString());
            return result;
        }

        @Override
        protected void onPostExecute(final ArrayList<Article> sources) {
            super.onPostExecute(sources);
            Log.d("demo", sources.toString());
            articleArrayList = sources;
            CustomAdapter customAdapter = new CustomAdapter();
            listView.setAdapter(customAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(News.this, Webview.class);
                    intent.putExtra("StringName", articleArrayList.get(position).getUrl());
                    startActivity(intent);
                }
            });
            dialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

        class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return articleArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.source_list_template, null);

            Log.d("demo", "TESTING IN GET VIEW - " + articleArrayList.toString());
            Article article = articleArrayList.get(position);

            ImageView imageView = convertView.findViewById(R.id.imageView_toUrl);

            TextView textViewTitle = convertView.findViewById(R.id.textView_title);
            TextView textViewAuthor = convertView.findViewById(R.id.textView_author);
            TextView textViewDate = convertView.findViewById(R.id.textView_date);

            Picasso.with(News.this).load(article.getUrlToimage()).into(imageView);
            textViewTitle.setText(article.getTitle());
            if (article.getAuthor().equals("null")) {
                textViewAuthor.setText(R.string.noAuthor);
            } else if (article.getAuthor() != null) {
                textViewAuthor.setText(article.getAuthor());
            }
            textViewDate.setText(article.getPublishedAt());

            return convertView;
        }
    }
}
