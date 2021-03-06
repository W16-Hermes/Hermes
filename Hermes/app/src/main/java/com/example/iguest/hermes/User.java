package com.example.iguest.hermes;

/**
 * Created by iguest on 3/6/16.
 */
public class User {
    private String screenName;
    private String phoneNumber;
    private int score;

    public User(String name, String number, int score) {
        this.screenName = name;
        this.phoneNumber = number;
        this.score = score;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String toString() {
        return screenName + "'s score is " +  score;
    }
}
