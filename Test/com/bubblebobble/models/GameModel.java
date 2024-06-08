package com.bubblebobble.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameModel {
    private PlayerModel player;
    private ArrayList<PlatformModel> platforms;
    private EnemyModel enemyMod;
    private ArrayList<WallModel> walls;
    private PowerUpModel pwupModel;
    private HashMap<String , PowerUpModel> pwupHash;

    public GameModel(PlayerModel player, ArrayList<PlatformModel> platforms, EnemyModel enemyMod,
            ArrayList<WallModel> walls, PowerUpModel pwupModel, HashMap<String , PowerUpModel> pwupHash) {
        this.player = player;
        this.platforms = platforms;
        this.enemyMod = enemyMod;
        this.walls = walls;
        this.pwupModel = pwupModel;
        this.pwupHash = pwupHash;
    }

    public PlayerModel getPlayer() {
        return this.player;
    }

    public ArrayList<PlatformModel> getPlatforms() {
        return platforms;
    }

    public ArrayList<WallModel> getWallModels() {
        return walls;
    }

    public EnemyModel getEnemyModel() {
        return enemyMod;
    }

    public PowerUpModel getPwupModel() {
        return pwupModel;
    }

    public boolean isPowerupActive(String codice) {
        for (Map.Entry<String, PowerUpModel> entry : pwupHash.entrySet()) {
            String key = entry.getKey();
            PowerUpModel valModel = (PowerUpModel) entry.getValue();

            if (key.equals(codice) && valModel.isActive() && !valModel.isExpired())
                return true;

        }

        return false;
    }
}
