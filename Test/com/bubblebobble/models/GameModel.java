package com.bubblebobble.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bubblebobble.Constants;
import com.bubblebobble.levels.Level;

public class GameModel {
    private ScoreModel score;
    private PlayerModel player;

    private ArrayList<WallModel> walls;
    private ArrayList<EnemyModel> enemies;
    private ArrayList<PlatformModel> platforms;
    private ArrayList<ProjectileModel> projectiles;
    private HashMap<String, PowerUpModel> powerUps;

    private static GameModel instance;

    private GameModel() {
        score = new ScoreModel();
        player = new PlayerModel(Constants.MAX_WIDTH / 3,
                Constants.MAX_HEIGHT * 70 / 100 - Constants.ALL_PLATFORMHEIGHT);
    }

    public static GameModel getInstance() {
        if (instance == null)
            instance = new GameModel();

        return instance;
    }

    public void loadLevel(Level level) {
        // azzerriamo gli oggetti del vecchio livello
        walls = new ArrayList<>();
        powerUps = new HashMap<>();
        enemies = new ArrayList<>();
        platforms = new ArrayList<>();
        projectiles = new ArrayList<>();

        // carichiamo la nuova mappa
        level.load(this);

        // riposizioniamo il giocatore nel punto iniziale
        player.setX(Constants.MAX_WIDTH / 3);
        player.setY(Constants.MAX_HEIGHT * 70 / 100 - Constants.ALL_PLATFORMHEIGHT);
    }

    // === Player
    public PlayerModel getPlayer() {
        return this.player;
    }

    // === Projectiles
    public GameModel addProjectile(ProjectileModel projectile) {
        this.projectiles.add(projectile);
        return this;
    }

    public List<ProjectileModel> getProjectiles() {
		return projectiles;
	}

    // === Platforms
    public GameModel addPlatform(PlatformModel platform) {
        this.platforms.add(platform);
        return this;
    }

    public ArrayList<PlatformModel> getPlatforms() {
        return platforms;
    }

    // === Walls
    public GameModel addWall(WallModel wall) {
        this.walls.add(wall);
        return this;
    }

    public ArrayList<WallModel> getWalls() {
        return walls;
    }

    // === Enemy
    public GameModel addEnemy(EnemyModel enemy) {
        this.enemies.add(enemy);
        return this;
    }

    public ArrayList<EnemyModel> getEnemies() {
        return this.enemies;
    }

    // === PowerUps
    public HashMap<String, PowerUpModel> getPowerUps() {
        return this.powerUps;
    }

    public GameModel addPowerUp(String codice, PowerUpModel powerUp) {
        powerUps.put(codice, powerUp);
        return this;
    }

    public boolean hasPowerup(String codice) {
        PowerUpModel pwup = powerUps.get(codice);
        return pwup != null && pwup.isActive() && !pwup.isExpired();
    }

    // === Score
    public ScoreModel getScore() {
        return score;
    }
}
