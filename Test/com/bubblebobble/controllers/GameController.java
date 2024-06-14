package com.bubblebobble.controllers;

import com.bubblebobble.BubbleBobble;
import com.bubblebobble.Constants;
import com.bubblebobble.contansts.Events;
import com.bubblebobble.contansts.PowerUpType;
import com.bubblebobble.levels.Level;
import com.bubblebobble.levels.LevelManager;
import com.bubblebobble.models.*;
import com.bubblebobble.views.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {
    private GameView game;
    private boolean isOver;
    private GameModel model;
    private PlayerModel player;
    private BubbleBobble window;
    private Random random = new Random();
    /**
     * Metodo costruttore vuoto del GameController, prende le istanze già inizializzate di GameModel e PlayerModel
     * (Quella di PlayerModel è contenuta in GameModel, come altre istanze di models)
     */
    public GameController(BubbleBobble game) {
        model = GameModel.getInstance();
        player = model.getPlayer();
        window = game;
    }

    public GameView getGame() {
        return game;
    }

    public GameModel getGameModel() {
        return model;
    }
    /**
     * Crea una nuova istanza del GameView.
     * Il costruttore del GameView richiede un'istanza di GameModel, per ottenerla potrebbe essere utile usare il metodo
     * "getInstance()" da GameModel
     */
    public void startGame() {
        isOver = false;
        game = new GameView(model);
        changeLevel(LevelManager.getStartLevel());
    }
    /**
     * Questo metodo viene usato per cambiare il livello (chiamato quando il giocatore uccide tutti i nemici nel
     * livello.)
     * @param level il livello da caricare per cambiare il livello attuale.
     */
    public void changeLevel(Level level) {
        System.err.println("Level " + level.getLevel() + " started.");
        model.loadLevel(level);
        game.onChangeLevel();
    }
    /**
     * Attiva un powerup in base a quello dato in parametro.
     * Crea una nuova istanza di PowerUpType se viene dato un parametro non valido.
     * @param powerUpType Il powerup da attivare, attenzione: deve essere di tipo "PowerUpType"
     */
    private void activatePowerUp(PowerUpType powerUpType) {
        switch (powerUpType) {
            case Health:
                player.increaseLife();
                break;

            case Random:
                PowerUpType[] powerUpTypes = new PowerUpType[] {
                        PowerUpType.Speed,
                        PowerUpType.SuperJump,
                        PowerUpType.Invincibility,
                        PowerUpType.DoublePoints
                        //PowerUpType.Health
                };

                activatePowerUp(powerUpTypes[random.nextInt(powerUpTypes.length)]);
                break;

            case KillThemAll:
                killAllEnemies();
                break;

            default:
                model.activatePowerUp(new ActivePowerUpModel(powerUpType));
                break;
        }
    }
    /**
     * Uccide tutti i nemici nel livello
     */
    private void killAllEnemies() {
        for (EnemyModel enemy : model.getEnemies()) {
            enemy.instaKill();
        }
    }
    /**
     * Rimuove i powerup utilizzati, facendo scomparire il loro render e la possibilità di essere raccolti.
     */
    private void updatePowerUps() {
        // remove expired powerups
        for (ActivePowerUpModel pwup : model.getActivePowerUpModels().stream().toList()) {
            if (pwup.isExpired()) {
                model.removePowerUp(pwup);
            }
        }

        // check collected powerups
        List<PowerUpModel> collectedPowerUps = model.getPowerUps().stream().filter(pwup -> pwup.collidesWith(player))
                .toList();

        if (!collectedPowerUps.isEmpty()) {
            for (PowerUpModel pwup : collectedPowerUps) {
                activatePowerUp(pwup.getPowerUpType());
                model.removePowerUp(pwup);
            }

            model.notify(Events.COLLECT_POWERUP, new ActionEvent(this, 0, "updatePowerUps"));
        }
    }

    // eseguito ad ogni frame
    // qui dobbiamo sia aggiornare i modelli (es. fare il move) che fare il render
    // della view
    /**
    * eseguito ad ogni frame, ciclo principale del gioco oltre al main
    */
    public void onTick() {
        updatePlayer();
        updatePowerUps();
        updateProjectiles();
        updateEnemies();
        checkGravity();
        updateGame();
    }
    /**
     * Metodo che controlla se tutti i nemici sono stati eliminati e in tal caso aggiorna il livello chiamando
     * getNextLevel() da LevelManager
     */
    private void updateGame() {
        // se tutti i nemici sono stati eliminati, ricomincia il livello
        if (model.getEnemies().isEmpty()) {
            Level nextLevel = LevelManager.getNextLevel(model.getCurrentLevel());

            if (nextLevel != null) {
                changeLevel(nextLevel);
            } else {
                onEndGame();
            }
        }
    }
    /**
     * Aggiorna il movimento del giocatore e controlla le collisioni.
     */
    private void updatePlayer() {
        // check walls
        for (WallModel wall : model.getWalls()) {
            if (wall.collidesWith(player)) {
                boolean isLeftWall = wall.getX() <= player.getX();

                if (isLeftWall) {
                    player.setX(wall.getX() + wall.getWidth());
                } else {
                    player.setX(wall.getX() - player.getWidth());
                }
            }
        }

        // check collisions with projectiles
        checkProjectileCollisions(player);

        // update player movement
        player.move();
    }
    /**
     * Gestisce l'evento di un giocatore colpito da un nemico.
     */
    private void onHitPlayerByEnemy() {
        if (!model.hasPowerup(PowerUpType.Invincibility)) {
            player.decreaseLife();
            player.setX(Constants.MAX_WIDTH / 3);
            player.setY(Constants.MAX_HEIGHT * 70 / 100 - Constants.PLATFORM_HEIGHT);
            model.activatePowerUp(new ActivePowerUpModel(PowerUpType.Invincibility));
        }

        if (player.getLives() <= 0) {
            onGameOver();
        }
    }

    private void onEndGame() {
        if (!isOver) {
            // incrementa il numero di game vinti
            if (model.getProfile() != null)
                model.getProfile().setWonGams(model.getProfile().getWonGams() + 1);

            model.save();
            isOver = true;
            window.showEndGame();
        }
    }

    private void onGameOver() {
        if (!isOver) {
            // incrementa il numero di game persi
            if (model.getProfile() != null)
                model.getProfile().setLostGames(model.getProfile().getLostGames() + 1);

            model.save();
            isOver = true;
            window.showGameOver();
        }
    }
    /**
     * Controlla la gravità per le entità del gioco e aggiorna le loro posizioni.
     */
    private void checkGravity() {
        // Regola la posizione del personaggio per farlo rimanere sulla piattaforma
        ArrayList<CharacterModel> entities = new ArrayList<>();
        entities.addAll(model.getEnemies().stream().filter(e -> !(e instanceof GhostEnemyModel)).toList());
        entities.add(player);

        for (CharacterModel entity : entities) {
            boolean isAbovePlatform = false;
            for (PlatformModel platform : model.getPlatforms()) {
                boolean isAbove = entity.getYSpeed() >= 0
                        && entity.collidesWith(platform)
                        && entity.getY() + entity.getHeight() >= platform.getY()
                        && entity.getY() <= platform.getY() + platform.getHeight()
                        && entity.getY() + entity.getHeight() <= platform.getY() + platform.getHeight();

                if (isAbove) {
                    int destY = platform.getY() - entity.getHeight();
                    if (destY != entity.getY()) {
                        entity.setY(destY);
                        entity.setYSpeed(0); // Imposta la velocità verticale a 0 per fermare il movimento verso il
                                             // basso
                        entity.setJumping(false); // Imposta lo stato del salto a falso
                        isAbovePlatform = true;
                    }
                }
            }

            // l'entità che cade nel vuoto, spawna dall'alto
            if (entity.getY() >= Constants.MAX_HEIGHT) {
                entity.setY(0);
            }

            if (isAbovePlatform) {
                entity.setYSpeed(0);
            } else if (entity instanceof PlayerModel) {
                // aumenta la velocità di caduta progressivamente
                entity.setYSpeed(entity.getYSpeed() + 1);
            } else if (entity instanceof EnemyModel && !((EnemyModel) entity).isInBubble()) {
                // aumenta la velocità di caduta progressivamente
                entity.setYSpeed(3);
            }
        }
    }
    /**
     * Aggiorna i proiettili nel gioco, controlla le collisioni e rimuove i proiettili disattivati.
     */
    public void updateProjectiles() {
        for (ProjectileModel projectile : model.getProjectiles()) {
            if (projectile.isActive()) {
                projectile.move();
            }
        }

        // remove deactivated projectiles
        List<ProjectileModel> deactivatedProjectiles = model.getProjectiles().stream().filter(p -> !p.isActive())
                .toList();
        for (ProjectileModel projectile : deactivatedProjectiles) {
            model.removeProjectile(projectile);
        }
    }
    /**
     * Controlla se il giocatore può sparare un nuovo proiettile in base al tempo trascorso dall'ultimo sparo.
     * 
     * @return true se il giocatore può sparare, false altrimenti.
     */
    public boolean canShootProjectile() {
        List<ProjectileModel> projectiles = model.getProjectiles();

        if (projectiles.isEmpty())
            return true;

        ProjectileModel lastProjectile = projectiles.get(projectiles.size() - 1);
        long timePassed = System.currentTimeMillis() - lastProjectile.getActivationTime();
        long shootDelay = model.hasPowerup(PowerUpType.FastShoot) ? Constants.SHORT_SHOOT_DELAY : Constants.SHOOT_DELAY;

        return timePassed >= shootDelay;
    }
    /**
     * Gestisce l'evento di un tasto premuto.
     * 
     * @param e l'evento della tastiera.
     */
    public void onKeyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        int currentSpeed = model.hasPowerup(PowerUpType.Speed) ? Constants.PLAYER_BOOSTED_SPEED
                : Constants.PLAYER_DEFAULT_SPEED;

        if (key == KeyEvent.VK_LEFT) {
            player.setXSpeed(-currentSpeed);
        } else if (key == KeyEvent.VK_RIGHT) {
            player.setXSpeed(currentSpeed);
        } else if (key == KeyEvent.VK_UP) {
            player.jump(model.hasPowerup(PowerUpType.SuperJump) ? 23 : 18);
            if (model.hasPowerup(PowerUpType.JumpPoints)) {
                model.getScore().addPoints(100);
            }
        } else if (key == KeyEvent.VK_SPACE && canShootProjectile()) {
            player.shoot();
        }
    }
    /**
     * Gestisce l'evento di un tasto rilasciato.
     * 
     * @param e l'evento della tastiera.
     */
    public void onKeyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            player.setXSpeed(0);
        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            player.setYSpeed(0);
        }
    }

    // === Enemy
    /**
     * Aggiorna i nemici nel gioco, gestisce i loro movimenti e controlla le collisioni.
     */
    private void updateEnemies() {
        for (EnemyModel enemy : model.getEnemies()) {
            enemy.update();
            checkPlayerCollisions(enemy);
            checkProjectileCollisions(enemy);
        }

        removeEatenEnemies();
    }
    /**
     * Controlla le collisioni tra il giocatore e i nemici.
     * 
     * @param enemy il nemico da controllare per le collisioni.
     */
    private void checkPlayerCollisions(EnemyModel enemy) {
        if (player.collidesWith(enemy)) {
            // se ho il powerup freeze and kill il nemico muore quando viene toccato
            if (model.hasPowerup(PowerUpType.FreezeAndKill)) {
                enemy.instaKill();
                eatEnemy(enemy);
            }

            // se il nemico è vivo, il giocatore perde la vita
            if (!enemy.isFruit() && !enemy.isInBubble()) {
                onHitPlayerByEnemy();
            }

            // se il nemico è in una bolla ma non è un frutto, divennta un frutto
            else if (enemy.isInBubble() && !enemy.isFruit()) {
                enemy.setFruit(true);
            }

            // se non è una bolla ma è un frutto, facciamo sparire il nemico e guadagniamo
            // dei punti
            else if (!enemy.isInBubble() && enemy.isFruit() && !enemy.isEaten()) {
                eatEnemy(enemy);
            }
        }
    }
    /**
     * Mangia un nemico, rimuovendolo dal gioco e aggiungendo punti al punteggio del giocatore.
     * 
     * @param enemy il nemico da mangiare.
     */
    private void eatEnemy(EnemyModel enemy) {
        if (enemy.isEaten()) {
            throw new RuntimeException("Il nemico è stto già mangiato una volta");
        }

        enemy.setEaten(true);
        model.getScore().addPoints(100);
    }
    /**
     * Controlla le collisioni tra un'entità e i proiettili.
     * 
     * @param entity l'entità da controllare per le collisioni.
     */
    private void checkProjectileCollisions(CharacterModel entity) {
        List<ProjectileModel> projectiles = model.getProjectiles();
        for (ProjectileModel projectile : projectiles) {
            boolean shootByPlayer = projectile.getThrower() instanceof PlayerModel;

            if (projectile.collidesWith(entity)) {
                if (entity instanceof EnemyModel) {
                    EnemyModel enemy = (EnemyModel) entity;
                    if (shootByPlayer && !enemy.isFruit()) {
                        if (model.hasPowerup(PowerUpType.Instakill)) {
                            enemy.instaKill();
                        } else {
                            enemy.kill();
                        }
                        projectile.deactivate();
                    }
                } else if (entity instanceof PlayerModel) {
                    boolean shootByEnemy = projectile.getThrower() instanceof EnemyModel;
                    if (shootByEnemy) {
                        onHitPlayerByEnemy();
                        projectile.deactivate();
                    }
                }
            } else if (projectile.isActive()
                    && !(projectile instanceof BoomerangProjectileModel)
                    && model.getWalls().stream().anyMatch(wall -> wall.collidesWith(projectile))) {
                if (shootByPlayer)
                    model.getScore().addPoints(10);

                projectile.deactivate();
            }
        }
    }
    /**
     * Rimuove i nemici mangiati dal gioco.
     */
    private void removeEatenEnemies() {
        List<EnemyModel> eatenEnemies = model.getEnemies().stream().filter(EnemyModel::isEaten).toList();
        if (!eatenEnemies.isEmpty()) {
            // rimuove il nemico dal gioco
            for (EnemyModel enemyModel : eatenEnemies) {
                model.removeEnemy(enemyModel);
                enemyModel = null;
            }

            model.notify(Events.REMOVE_ENEMY, new ActionEvent(this, 0, "updateEnemies"));
        }
    }
}
