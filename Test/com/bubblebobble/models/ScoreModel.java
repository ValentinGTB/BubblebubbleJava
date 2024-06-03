package com.bubblebobble.models;

public class ScoreModel {
    private int currentScore;

    public int getCurrentScore() {
        return currentScore;
    }

    public void addPoints(int points) {
        currentScore += points;
    }

    public void resetScore() {
        currentScore = 0;
    }
}