package com.bubblebobble.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private Map<String, Set<ActionListener>> subscribersTopicMap;

    private static GameModel instance;

    private GameModel() {
        score = new ScoreModel();
        subscribersTopicMap = new HashMap<String,Set<ActionListener>>();
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

    public GameModel removeEnemy(EnemyModel enemy) {
        if (!enemies.contains(enemy))
            throw new RuntimeException("Il nemico non fa parte della mappa attuale");

        enemies.remove(enemy);
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

    public void removePowerUp(String codice) throws RuntimeException {
        PowerUpModel powerUp = powerUps.get(codice);

        if (powerUp == null)
            throw new RuntimeException("PowerUp non disponibile.");

        powerUps.remove(codice);
    }

    public boolean hasPowerup(String codice) {
        PowerUpModel pwup = powerUps.get(codice);
        return pwup != null && pwup.isActive() && !pwup.isExpired();
    }

    // === Score
    public ScoreModel getScore() {
        return score;
    }

    // === Subscribe Pattern
    public void addSubscriber(String topic, ActionListener listener)
    {
        var subscribers = subscribersTopicMap.get(topic);

        if (subscribers == null)
            subscribersTopicMap.put(topic, new HashSet<ActionListener>());

        subscribersTopicMap.get(topic).add(listener);
    }

    public void notify(String topic, ActionEvent message) {
        var subscribers = subscribersTopicMap.get(topic);

        if (subscribers == null)
            return;

        for (ActionListener actionListener : subscribers) {
            actionListener.actionPerformed(message);
        }
    }
}
