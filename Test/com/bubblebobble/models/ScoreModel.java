package com.bubblebobble.models;

import com.bubblebobble.contansts.PowerUpType;
import java.awt.event.ActionEvent;
/**
 * Questa classe gestisce il punteggio.
 */
public class ScoreModel {
    private int currentScore;

    public int getCurrentScore() {
        return currentScore;
    }
    /**
     * Aggiunge punti al punteggio corrente. Se è attivo il power-up DoublePoints,
     * i punti aggiunti vengono raddoppiati.
     *
     * @param punti I punti da aggiungere.
     */
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