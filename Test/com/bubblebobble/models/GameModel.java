package com.bubblebobble.models;

import java.util.ArrayList;

public class GameModel {
    private PlayerModel player;
    private ArrayList<PlatformModel> platforms;
    private EnemyModel enemyMod;

    public GameModel(PlayerModel player, ArrayList<PlatformModel> platforms , EnemyModel enemyMod) {
        this.player = player;
        this.platforms = platforms;
        this.enemyMod = enemyMod;
    }

    public PlayerModel getPlayer() {
        return this.player;
    }

    public ArrayList<PlatformModel> getPlatforms() {
        return platforms;
    }

    public EnemyModel getEnemyModel()
    {
        return enemyMod;
    }

}
