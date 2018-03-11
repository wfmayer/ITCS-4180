package com.example.alexk.group3_hw02;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by AlexK on 3/5/2018.
 */

public class GetAPIAsync extends AsyncTask<String, Integer, ArrayList<Question>> {

    IData iData;
    ArrayList<Question> questionsArrayList;


    public GetAPIAsync(IData iData) {
        this.iData = iData;
    }

    @Override
    protected ArrayList<Question> doInBackground(String... strings) {
        HttpURLConnection connection = null;
        ArrayList<Question> result = new ArrayList<>();
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String json = IOUtils.toString(connection.getInputStream(), "UTF-8").trim();

                JSONObject root = new JSONObject(json);
                JSONArray questions = root.getJSONArray("questions");
                for (int i = 0; i < questions.length(); i++) {
                    JSONObject questionsJSONObject = questions.getJSONObject(i);
                    Question question = new Question();

                    question.setText(questionsJSONObject.getString("text"));

                    if (questionsJSONObject.has("image")) {
                        question.setUrlToImage(questionsJSONObject.getString("image"));
                    }

                    //Choice array
                    JSONObject choicesObject = questionsJSONObject.getJSONObject("choices");
                    JSONArray choicesArray = choicesObject.getJSONArray("choice");
                    int choicesLength = choicesArray.length();
                    ArrayList<String> temp = new ArrayList<String>();
                    for (int j = 0; j < choicesLength; j++) {
                        temp.add(choicesArray.getString(j));
                    }

                    question.setChoices(temp);
                    //End of Choice array

                    question.setAnswer(choicesObject.getInt("answer"));

                    result.add(question);
                }
            }
        } catch (Exception e) {
            //Handle Exceptions
        } finally {
            //Close the connections
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<Question> questions) {
        super.onPostExecute(questions);
        iData.handleData(questions);
    }

    public static interface IData {
        public void handleData(ArrayList<Question> data);
    }
}
