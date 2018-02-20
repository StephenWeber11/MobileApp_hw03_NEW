package com.uncc.mobileappdev.hw03;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Stephen Weber on 2/18/2018.
 */

public class DataHelpers {


    public static boolean isConnected(Activity activity){
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    public static ArrayList<Question> formatQuestionDetailsString(String questionString){
        ArrayList<Question> questionDetails = new ArrayList<>();

        if(questionString != null && questionString != "") {
            String[] questionDetailsArray = questionString.split(";");

            /* Not sure what the best way to do this is... But this works!!!*/
            for(int i=1; i < questionDetailsArray.length - 1; i++){
                if(questionDetailsArray[i].contains("?")){
                    Question question = new Question();
                    question.setQuestion(questionDetailsArray[i]);
                    question.setImageURL(questionDetailsArray[i+1]);

                    int index = i+2;
                    ArrayList<String> possibleAnswers = new ArrayList<>();
                    while(index < questionDetailsArray.length && !questionDetailsArray[index].contains("?")){
                        possibleAnswers.add(questionDetailsArray[index]);
                        index++;
                    }
                    possibleAnswers.remove(possibleAnswers.size()-1); //Remove the last entry
                    question.setAnswers(possibleAnswers);
                    question.setAnswerIndex(Integer.parseInt(questionDetailsArray[index-1].substring(0,1)));
                    questionDetails.add(question);

                }
            }
        }

        logQuestions(questionDetails); //For Testing & Debugging...
        return questionDetails;
    }

    private static void logQuestions(ArrayList<Question> questionDetails){
        for (Question question : questionDetails) {
            if(question.getQuestion() != null && question.getQuestion() != "") {
                Log.d("Questions", question.getQuestion());
                Log.d("ImageURL", question.getImageURL());
                for(String str : question.getAnswers()){
                    Log.d("Answer: ", str);
                }
                Log.d("AnswerIndex", question.getAnswerIndex()+"");
            }
        }
    }

}
