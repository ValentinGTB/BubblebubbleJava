package com.bubblebobble.models;

import java.awt.event.ActionEvent;

import com.bubblebobble.contansts.PowerUpType;

public class ScoreModel {
    private int currentScore;

    public int getCurrentScore() {
        return currentScore;
    }

    public void addPoints(int points) {
        if (GameModel.getInstance().hasPowerup(PowerUpType.DoublePoints))
            points *= 2;

        GameModel.getInstance().notify("addPoints", new ActionEvent(this, 0, "addPoints"));
        currentScore += points;
    }

    public void resetScore() {
        currentScore = 0;
    }
}