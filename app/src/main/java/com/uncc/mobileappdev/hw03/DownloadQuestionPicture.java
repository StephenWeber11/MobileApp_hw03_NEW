package com.uncc.mobileappdev.hw03;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by CHINU on 2/19/2018.
 */

public class DownloadQuestionPicture extends AsyncTask<String, Void, Bitmap> {
    IQuestionPicture activity;
    int questionIndex = 0;

    public DownloadQuestionPicture(IQuestionPicture activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.startProgress();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        activity.setupData(bitmap, questionIndex);
        activity.stopProgress(questionIndex);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            questionIndex = Integer.valueOf(params[1]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            return BitmapFactory.decodeStream(con.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    static public interface IQuestionPicture {
        public void setupData(Bitmap image, int questionIndex);

        public void startProgress();

        public void stopProgress(int questionIndex);
    }
}
