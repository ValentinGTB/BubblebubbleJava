package com.bubblebobble.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.bubblebobble.Constants;
import com.bubblebobble.contansts.PowerUpType;
import com.bubblebobble.levels.Level;

public class GameModel {
    private Level currentLevel;
    private ScoreModel score;
    private PlayerModel player;
    private ProfileModel profile;

    private ArrayList<WallModel> walls;
    private ArrayList<EnemyModel> enemies;
    private ArrayList<PowerUpModel> powerUps;
    private ArrayList<PlatformModel> platforms;
    private ArrayList<ProjectileModel> projectiles;

    private ArrayList<ActivePowerUpModel> activePowerUps;

    private Map<String, Set<ActionListener>> subscribersTopicMap;

    private static GameModel instance;
    /**
     * Inizializza il punteggio, il giocatore e l'ActionListener per {@code subscribersTopicMap}
     * 
     * La classe {@code GameModel} contiene le istanze di tutti i model nel gioco. 
     * Basata su pattern MVC questa classe ha il compito di, attraverso getter e setter, gestire queste istanze e 
     * restituirle quando necessario. 
     */
    private GameModel() {
        score = new ScoreModel();
        subscribersTopicMap = new HashMap<String,Set<ActionListener>>();
        player = new PlayerModel(Constants.MAX_WIDTH / 3,
                Constants.MAX_HEIGHT * 70 / 100 - Constants.PLATFORM_HEIGHT);
    }

    public static GameModel getInstance() {
        if (instance == null)
            instance = new GameModel();

        return instance;
    }
    /**
     * Azzera tutti gli oggetti e powerup del livello precedente per costruirne di nuovi e caricare un nuovo livello.
     * @param level il livello da caricare.
     */
    public void loadLevel(Level level) {
        // azzerriamo gli oggetti del vecchio livello
        walls = new ArrayList<>();
        enemies = new ArrayList<>();
        powerUps = new ArrayList<>();
        platforms = new ArrayList<>();
        projectiles = new ArrayList<>();
        
        // azzerriamo i powerup attivi
        activePowerUps = new ArrayList<>();

        // carichiamo la nuova mappa
        level.load(this);

        // riposizioniamo il giocatore nel punto iniziale
        player.setX(Constants.MAX_WIDTH / 3);
        player.setY(Constants.MAX_HEIGHT * 70 / 100 - Constants.PLATFORM_HEIGHT);
    
        currentLevel = level;
    }

    // === Player
    public PlayerModel getPlayer() {
        return this.player;
    }
    /**
     * Restituisce il profilo del giocatore.
     * @return il profilo del giocatore
     */
    public ProfileModel getProfile() {
        return this.profile;
    }
   /**
     * Imposta il profilo del giocatore.
     * @param profile il profilo del giocatore da impostare
     */
    public void setProfile(ProfileModel profile) {
        this.profile = profile;
    }
    /**
     * Salva lo stato attuale del gioco, inclusi il punteggio del giocatore nel profilo.
     */
    public void save() {
        if (this.profile == null) {
            return;
        }

        // aggiorna lo score dell'utente
        if (score.getCurrentScore() > profile.getHighestScore()) {
            profile.setHighestScore(score.getCurrentScore());
        }

        try {
            profile.save();
        } catch (Exception e) {
            System.out.println("Impossibile salvare le statistiche del giocatore.");
        }
    }

    // === Projectiles
    public GameModel addProjectile(ProjectileModel projectile) {
        this.projectiles.add(projectile);
        return this;
    }

    public GameModel removeProjectile(ProjectileModel projectile) {
        this.projectiles.remove(projectile);
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
    /**
     * Rimuove un nemico.
     * @param enemy il nemico da rimuovere
     * @return l'istanza corrente di {@code GameModel}
     * @throws RuntimeException se il nemico non è presente nella mappa attuale
     */
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
    public ArrayList<PowerUpModel> getPowerUps() {
        return this.powerUps;
    }

    public GameModel addPowerUp(PowerUpModel powerUp) {
        powerUps.add(powerUp);
        return this;
    }

    public void removePowerUp(PowerUpModel model) throws RuntimeException {
        Optional<PowerUpModel> powerUp = powerUps.stream().filter(pwup -> pwup.equals(model)).findFirst();

        if (!powerUp.isPresent())
            throw new RuntimeException("PowerUp non accessibile.");

        powerUps.remove(powerUp.get());
    }
    /**
     * Controlla se un certo powerup di tipo {@code PowerUpType} è attivo
     * @param powerUpType il powerup da controllare
     * @return {@code true} se il power-up è attivo, {@code false} altrimenti
     */
    public boolean hasPowerup(PowerUpType powerUpType) {
        Optional<ActivePowerUpModel> model = activePowerUps.stream().filter(pwup -> pwup.is(powerUpType)).findFirst();
        return model.isPresent() && model.get().isActive() && !model.get().isExpired();
    }
    /**
     * Attiva un certo modello di powerup.
     * @param powerUpModel il modello di powerup da attivare
     * @return l'istanza corrente di {@code GameModel}
     */
    public GameModel activatePowerUp(ActivePowerUpModel powerUpModel) {
        // attiva l'effetto del powerup
        activePowerUps.add(powerUpModel);
        return this;
    }

    public List<ActivePowerUpModel> getActivePowerUpModels() {
        return activePowerUps;
    }
    /**
     * Rimuove un powerup.
     * @param powerUpModel powerup da rimuovere
     */
    public GameModel removePowerUp(ActivePowerUpModel powerUpModel) {
        activePowerUps.remove(powerUpModel);
        return this;
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

    // === Level
    public Level getCurrentLevel() {
        return currentLevel;
    }
}
