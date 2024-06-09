package com.bubblebobble.models;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreModel {
    private int currentScore;

    public int getCurrentScore() {
        return currentScore;
    }

    public void addPoints(int points) {
        if (GameModel.getInstance().hasPowerup("doppipunti"))
            points *= 2;

        GameModel.getInstance().notify("addPoints", new ActionEvent(this, 0, "addPoints"));
        currentScore += points;
    }

    public void resetScore() {
        currentScore = 0;
    }
}