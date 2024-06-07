package com.bubblebobble.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreModel {

    HashMap<String, ArrayList<Object>> pwupHash;

    public ScoreModel(HashMap pwupHash)
    {
        this.pwupHash = pwupHash;
    }

    private int currentScore;

    public int getCurrentScore() {
        return currentScore;
    }

    public void addPoints(int points) {
        
        for(Map.Entry<String, ArrayList<Object>> entry : pwupHash.entrySet())
            {
                String key = entry.getKey();
                PowerUpModel valModel = (PowerUpModel) entry.getValue().get(0);

                if(key.equals("doppipunti"))
                {
                    if(valModel.isActive() && !valModel.isExpired()) currentScore += points * 2; 
                }

            }

        currentScore += points;
    }

    public void resetScore() {
        currentScore = 0;
    }
}