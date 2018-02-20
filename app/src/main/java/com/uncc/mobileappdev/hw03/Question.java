package com.uncc.mobileappdev.hw03;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Stephen Weber on 2/18/2018.
 */

public class Question implements Serializable {

    private static final long serialversionUID = 1L;

    private int questionIndex;
    private String question;
    private String imageURL;
    private ArrayList<String> answers = new ArrayList<>();
    private int answerIndex = Integer.MAX_VALUE;

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }
}
