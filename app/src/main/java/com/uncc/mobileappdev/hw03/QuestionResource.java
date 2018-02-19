package com.uncc.mobileappdev.hw03;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.uncc.mobileappdev.hw03.DataHelpers.formatQuestionDetailsString;

/**
 * Created by Stephen Weber on 2/18/2018.
 */

public class QuestionResource extends AsyncTask<String, Void, ArrayList<Question>> {
    public AsyncResponse delegate = null;
    IData activity;
    private ArrayList<Question> questionData = new ArrayList<>();

    public QuestionResource(IData activity){
        this.activity = activity;
    }

    @Override
    protected ArrayList<Question> doInBackground(String... params) {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        String result = null;
        try {

            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            result = sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }

        questionData = formatQuestionDetailsString(result);
        return questionData;
    }

    @Override
    protected  void onPostExecute(ArrayList<Question> questionData){
        if(questionData != null && !questionData.isEmpty()){
            activity.setupData(questionData);
            Log.d("Demo", "Number of Question objects in list: " + questionData.size());
        } else {
            Log.d("Demo", "NO DATA!");
        }
    }
    static public interface IData {
        public void setupData(ArrayList<Question> result);
    }
}
