package com.bubblebobble.levels;

import com.bubblebobble.Constants;
import com.bubblebobble.contansts.PowerUpType;
import com.bubblebobble.models.BaseEnemyModel;
import com.bubblebobble.models.EnemyModel;
import com.bubblebobble.models.GameModel;
import com.bubblebobble.models.PlatformModel;
import com.bubblebobble.models.PowerUpModel;
import com.bubblebobble.models.WallModel;

public class Level06 implements Level {
    public int getLevel() {
        return 6;
    }

    private void loadBorders(GameModel game) {
        // muri esterni
        game.addWall(new WallModel(0, 0, Constants.WALL_WIDTH, Constants.MAX_HEIGHT));
        game.addWall(new WallModel(Constants.MAX_WIDTH - Constants.WALL_WIDTH, 0, Constants.WALL_WIDTH, Constants.MAX_HEIGHT));
    }

    private void loadTopBottomPlatforms(GameModel game) {
        // piattaforme top e bottom
        game.addPlatform(new PlatformModel(0, 0, Constants.MAX_WIDTH, Constants.PLATFORM_HEIGHT));
        game.addPlatform(new PlatformModel(0, Constants.MAX_HEIGHT - Constants.PLATFORM_HEIGHT, Constants.MAX_WIDTH, Constants.PLATFORM_HEIGHT));
    }

    private void loadPlatforms(GameModel game) {
        // piattaforma
        game.addPlatform(new PlatformModel(Constants.MAX_WIDTH - Constants.PLATFORM_WIDTH * 10 - Constants.PLATFORM_WIDTH, Constants.MAX_HEIGHT * 80 / 100, 40 * 10,
                Constants.PLATFORM_HEIGHT));
    }

    private void loadPowerUps(GameModel game)
    {
        // game.addPowerUp(new PowerUpModel(PowerUpType.Speed, 500, 500, 40, 40));
        // game.addPowerUp(new PowerUpModel(PowerUpType.Instakill, 150, 680, 40, 40));
        // game.addPowerUp(new PowerUpModel(PowerUpType.SuperJump, 700, 600, 40, 40));
        // game.addPowerUp(new PowerUpModel(PowerUpType.DoublePoints, 500, 700, 40, 40));
        // game.addPowerUp(new PowerUpModel(PowerUpType.KillThemAll, 500, 400, 40, 40));
        // game.addPowerUp(new PowerUpModel(PowerUpType.Freeze, 700, 500, 40, 40));
        // game.addPowerUp(new PowerUpModel(PowerUpType.FreezeAndKill, 700, 700, 40, 40));
        // game.addPowerUp(new PowerUpModel(PowerUpType.JumpPoints, 200, 700, 40, 40));
        // game.addPowerUp(new PowerUpModel(PowerUpType.FastShoot, 200, 650, 40, 40));
        // game.addPowerUp(new PowerUpModel(PowerUpType.Health, 150, 680, 40, 40));
        // game.addPowerUp(new PowerUpModel(PowerUpType.Invincibility, 150, 680, 40, 40));
        game.addPowerUp(new PowerUpModel(PowerUpType.Random, 150, 680, 40, 40));
    }

    private void loadEnemies(GameModel game)
    {
        EnemyModel enemy = new BaseEnemyModel(70, 680);
        game.addEnemy(enemy);

        // EnemyModel enemy2 = new EnemyModel(90, 400);
        // game.addEnemy(enemy2);

        // EnemyModel enemy3 = new EnemyModel(game.getPlayer(), game.getWalls(), game.getPlatforms(), 100, 300);
        // game.addEnemy(enemy3);
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
