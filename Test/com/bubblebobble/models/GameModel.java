package com.bubblebobble.models;

import java.util.ArrayList;

public class GameModel {
    private PlayerModel player;
    private ArrayList<PlatformModel> platforms;
    private EnemyModel enemyMod;
    private ArrayList<WallModel> walls;
    private PowerUpModel pwupModel;


    public GameModel(PlayerModel player, ArrayList<PlatformModel> platforms , EnemyModel enemyMod, ArrayList<WallModel> walls , PowerUpModel pwupModel) {
        this.player = player;
        this.platforms = platforms;
        this.enemyMod = enemyMod;
        this.walls = walls;
        this.pwupModel = pwupModel;
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

    public EnemyModel getEnemyModel()
    {
        return enemyMod;
    }

    public PowerUpModel getPwupModel()
    {
        return pwupModel;
    }

}
