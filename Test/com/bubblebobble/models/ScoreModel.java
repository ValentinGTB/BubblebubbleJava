package com.bubblebobble.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreModel {
    GameModel game;

    public ScoreModel(GameModel game) {
        this.game = game;
    }

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