package com.uncc.mobileappdev.hw03;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements QuestionResource.IData {

    private Button btnExit, btnStartTrivia;
    private ProgressBar pgBarLoading;
    private ImageView imgViewTrivia;
    private TextView txtViewLoadingReady;
    private ArrayList<Question> questionList;
    public static final String QUESTIONS_LIST_KEY = "QUESTIONS";
    private ArrayList<Question> questionDetails = new ArrayList<Question>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnExit = (Button) findViewById(R.id.action_exit);
        btnStartTrivia = (Button) findViewById(R.id.action_start_trivia);
        imgViewTrivia = (ImageView) findViewById(R.id.image_view_trivia);
        pgBarLoading = (ProgressBar) findViewById(R.id.progress_bar_loading);
        txtViewLoadingReady = (TextView) findViewById(R.id.text_loading_ready);
        btnStartTrivia.setEnabled(false);

        questionList = new ArrayList<Question>();

        if (isConnectedOnline() && questionList.isEmpty()) {
            pgBarLoading.setVisibility(View.VISIBLE);
            txtViewLoadingReady.setText(getResources().getString(R.string.progress_bar_loading));
            new QuestionResource(this).execute("http://dev.theappsdr.com/apis/trivia_json/trivia_text.php");
        } else if (isConnectedOnline()) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.error_no_internet), Toast.LENGTH_SHORT).show();
        }
    }


    public void startTrivia(View view) {
        Intent intent = new Intent(MainActivity.this, TriviaActivity.class);
        intent.putExtra(QUESTIONS_LIST_KEY, questionDetails);
        startActivity(intent);
    }


    /**
     * Handler for exit
     * @param view
     */
    public void exit(View view) {
        finish();
        System.exit(0);
    }

    /**
     * Checking whether the internet connection is available
     * @return
     */
    private boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void setupData(ArrayList<Question> result) {
        pgBarLoading.setVisibility(View.INVISIBLE);
        txtViewLoadingReady.setText("");
        if (!result.isEmpty()) {

            txtViewLoadingReady.setText(getResources().getString(R.string.progress_bar_ready));
            imgViewTrivia.setImageResource(R.drawable.trivia);
            btnStartTrivia.setEnabled(true);
            questionDetails.addAll(result);
            //Your Question list is available here
        } else {
            txtViewLoadingReady.setText(getResources().getString(R.string.error_no_questions));
        }

    }
}
