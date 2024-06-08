package com.bubblebobble.levels;

import com.bubblebobble.Constants;
import com.bubblebobble.models.EnemyModel;
import com.bubblebobble.models.GameModel;
import com.bubblebobble.models.PlatformModel;
import com.bubblebobble.models.PowerUpModel;
import com.bubblebobble.models.WallModel;

public class Level01 implements Level {
    private void loadBorders(GameModel game) {
        // muri esterni
        game.addWall(new WallModel(0, 0, Constants.ALL_PLATFORMHEIGHT, Constants.MAX_WIDTH));
        game.addWall(new WallModel(Constants.MAX_WIDTH - 55, 0, Constants.ALL_PLATFORMHEIGHT, Constants.MAX_WIDTH));
    }

    private void loadTopBottomPlatforms(GameModel game) {
        // piattaforme top e bottom
        game.addPlatform(new PlatformModel(40, Constants.MAX_HEIGHT * 90 / 100, Constants.MAX_WIDTH - 95,
                Constants.ALL_PLATFORMHEIGHT));
        game.addPlatform(new PlatformModel(40, Constants.MAX_HEIGHT * 0 / 100, Constants.MAX_WIDTH - 95,
                Constants.ALL_PLATFORMHEIGHT));
    }

    private void loadPlatforms(GameModel game) {
        // piattaforma
        game.addPlatform(new PlatformModel(500, Constants.MAX_HEIGHT * 75 / 100, Constants.MAX_WIDTH / 2 - 105,
                Constants.ALL_PLATFORMHEIGHT));
    }

    private void loadPowerUps(GameModel game)
    {
        PowerUpModel pwupModel = new PowerUpModel(500, 500, 40, 40, null);
        game.addPowerUp("velocita", pwupModel);

        PowerUpModel pwupModel2 = new PowerUpModel(100, 600, 40, 40, null);
        game.addPowerUp("instakill", pwupModel2);

        PowerUpModel pwupModel3 = new PowerUpModel(700, 600, 40, 40, null);
        game.addPowerUp("superjump", pwupModel3);

        PowerUpModel pwupModel4 = new PowerUpModel(500, 700, 40, 40, null);
        game.addPowerUp("doppipunti", pwupModel4);

        PowerUpModel pwupModel5 = new PowerUpModel(500, 400, 40, 40, null);
        game.addPowerUp("killthemall", pwupModel5);

        PowerUpModel pwupModel6 = new PowerUpModel(700, 500, 40, 40, null);
        game.addPowerUp("freeze", pwupModel6);

        PowerUpModel pwupModel7 = new PowerUpModel(700, 700, 40, 40, null);
        game.addPowerUp("freezeAndKill", pwupModel7);

        PowerUpModel pwupModel8 = new PowerUpModel(200, 700, 40, 40, null);
        game.addPowerUp("jumpPoints", pwupModel8);
    }

    private void loadEnemies(GameModel game)
    {
        EnemyModel enemy = new EnemyModel(game.getPlayer(), game.getWalls(), game.getPlatforms(), 70, 680);
        game.addEnemy(enemy);

        EnemyModel enemy2 = new EnemyModel(game.getPlayer(), game.getWalls(), game.getPlatforms(), 90, 400);
        game.addEnemy(enemy2);

        EnemyModel enemy3 = new EnemyModel(game.getPlayer(), game.getWalls(), game.getPlatforms(), 100, 300);
        game.addEnemy(enemy3);
    }

    public void load(GameModel game) {
        // carica informazioni mappa
        loadBorders(game);
        loadTopBottomPlatforms(game);
        loadPlatforms(game);
        loadPowerUps(game);
        loadEnemies(game);
    }
}
