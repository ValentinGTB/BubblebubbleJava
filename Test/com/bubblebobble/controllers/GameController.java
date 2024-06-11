package com.bubblebobble.controllers;

import com.bubblebobble.Constants;
import com.bubblebobble.contansts.Direction;
import com.bubblebobble.contansts.PowerUpType;
import com.bubblebobble.levels.Level;
import com.bubblebobble.levels.Level01;
import com.bubblebobble.levels.LevelManager;
import com.bubblebobble.models.*;
import com.bubblebobble.views.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameController {
    private GameView game;
    private GameModel model;
    private PlayerModel player;
    private Random random = new Random();

    public GameController() {
        model = GameModel.getInstance();
        player = model.getPlayer();
    }

    public GameView getGame() {
        return game;
    }

    public GameModel getGameModel() {
        return model;
    }

    public void startGame() {
        game = new GameView(model);
        changeLevel(LevelManager.getStartLevel());
    }

    public void changeLevel(Level level) {
        System.err.println("Level " + level.getLevel() + " started.");
        model.loadLevel(level);
        game.onChangeLevel();
    }

    private void activatePowerUp(PowerUpType powerUpType) {
        switch (powerUpType) {
            case Health:
                player.increaseLife();
                break;

            case RandomPowerUp:
                PowerUpType[] powerUpTypes = new PowerUpType[] {
                    // PowerUpType.Speed,
                    // PowerUpType.SuperJump,
                    // PowerUpType.Invincibility,
                    // PowerUpType.DoublePoints
                    PowerUpType.Health
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

    private void freezeEnemies() {
        for (EnemyModel enemy : model.getEnemies()) {
            enemy.instaKill();
        }
    }

    private void killAllEnemies() {
        for (EnemyModel enemy : model.getEnemies()) {
            enemy.instaKill();
        }
    }

    private void updatePowerUps() {
        // remove expired powerups
        for (ActivePowerUpModel pwup : model.getActivePowerUpModels().stream().toList()) {
            if (pwup.isExpired()) {
                model.removePowerUp(pwup);
            }
        }

        // check collected powerups 
        List<PowerUpModel> collectedPowerUps = model.getPowerUps().stream().filter(pwup -> pwup.collidesWith(player)).toList();

        if (!collectedPowerUps.isEmpty()) {
            for (PowerUpModel pwup : collectedPowerUps) {
                activatePowerUp(pwup.getPowerUpType());
                model.removePowerUp(pwup);
            }

            model.notify("collectPowerUp", new ActionEvent(this, 0, "updatePowerUps"));
        }
    }

    // eseguito ad ogni frame
    // qui dobbiamo sia aggiornare i modelli (es. fare il move) che fare il render
    // della view
    public void onTick() {
        updatePlayer();
        updatePowerUps();
        updateProjectiles();
        updateEnemies();
        checkGravity();
        updateGame();
    }

    private void updateGame() {
        // se tutti i nemici sono stati eliminati, ricomincia il livello
        if (model.getEnemies().isEmpty()) {
            Level nextLevel = LevelManager.getNextLevel(model.getCurrentLevel());

            if (nextLevel != null) {
                changeLevel(nextLevel);
            } else {
                Constants.isGamePaused = true;
            }
        }
    }

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

        player.move();
    }

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

            // TODO: avere un metodo specifico nell'entita che gestisca la gravità
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

    public void updateProjectiles() {
        for (ProjectileModel projectile : model.getProjectiles()) {
            if (projectile.isActive()) {
                projectile.move();
                // Puoi aggiungere la logica per disattivare il proiettile se esce dallo schermo
                // projectile.deactivate() se il proiettile esce dallo schermo
            }
        }
    }

    public boolean canShootProjectile() {
        List<ProjectileModel> projectiles = model.getProjectiles();

        if (projectiles.isEmpty())
            return true;

        ProjectileModel lastProjectile = projectiles.get(projectiles.size() - 1);
        long timePassed = System.currentTimeMillis() - lastProjectile.getActivationTime();
        long shootDelay = model.hasPowerup(PowerUpType.FastShoot) ? Constants.SHORT_SHOOT_DELAY : Constants.SHOOT_DELAY;

        return timePassed >= shootDelay;
    }

    public void onKeyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        int currentSpeed = model.hasPowerup(PowerUpType.Speed) ? Constants.PLAYER_BOOSTED_SPEED : Constants.PLAYER_DEFAULT_SPEED;

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

    public void onKeyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            player.setXSpeed(0);
        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            player.setYSpeed(0);
        }
    }

    // === Enemy
    private void updateEnemies() {
        for (EnemyModel enemy : model.getEnemies()) {
            enemy.update();
            checkPlayerCollisions(enemy);
            checkProjectileCollisions(enemy);
        }

        removeEatenEnemies();
    }

    private void checkPlayerCollisions(EnemyModel enemy) {
        if (player.collidesWith(enemy)) {
            // se ho il powerup freeze and kill il nemico muore quando viene toccato
            if (model.hasPowerup(PowerUpType.FreezeAndKill)) {
                enemy.instaKill();
                eatEnemy(enemy);
            }

            // se il nemico è vivo, il giocatore perde la vita
            if (!enemy.isFruit() && !enemy.isInBubble() && !model.hasPowerup(PowerUpType.Invincibility)) {
                player.decreaseLife();
                player.setX(Constants.MAX_WIDTH / 3);
                player.setY(Constants.MAX_HEIGHT * 70 / 100 - Constants.PLATFORM_HEIGHT);
                model.activatePowerUp(new ActivePowerUpModel(PowerUpType.Invincibility));
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

    private void eatEnemy(EnemyModel enemy) {
        if (enemy.isEaten()) {
            throw new RuntimeException("Il nemico è stto già mangiato una volta");
        }

        enemy.setEaten(true);
        model.getScore().addPoints(100);
    }

    private void checkProjectileCollisions(EnemyModel enemy) {
        List<ProjectileModel> projectiles = model.getProjectiles();
        for (ProjectileModel projectile : projectiles) {
            if (projectile.collidesWith(enemy) && !enemy.isFruit()) {
                if (model.hasPowerup(PowerUpType.Instakill)) {
                    enemy.instaKill();
                } else {
                    enemy.kill();
                }

                projectile.deactivate();
            }

            else if (projectile.isActive() && model.getWalls().stream().anyMatch(wall -> wall.collidesWith(projectile))) {
                model.getScore().addPoints(10);
                projectile.deactivate();
            }
        }
    }

    private void removeEatenEnemies() {
        List<EnemyModel> eatenEnemies = model.getEnemies().stream().filter(EnemyModel::isEaten).toList();
        if (!eatenEnemies.isEmpty()) {
            // rimuove il nemico dal gioco
            for (EnemyModel enemyModel : eatenEnemies) {
                model.removeEnemy(enemyModel);
                enemyModel = null;
            }

            model.notify("removeEnemy", new ActionEvent(this, 0, "updateEnemies"));
        }
    }

    public void blocchiDirezzionali(EnemyModel enemy) {
        blocchiBordiTopBottom(enemy);
    }

    public void blocchiBordiTopBottom(EnemyModel enemy) {
        if (player.getY() + player.getYSpeed() < 0) {
            player.setYSpeed(Constants.PLAYER_DEFAULT_SPEED);
        }
        if (player.getY() + player.getYSpeed() >= Constants.MAX_HEIGHT) {
            player.setY(-40);
            player.setYSpeed(-Constants.PLAYER_DEFAULT_SPEED);
        }

        // Blocco Enemy
        if (enemy.getY() + enemy.getYSpeed() < 0) {
            enemy.setYSpeed(Constants.PLAYER_DEFAULT_SPEED);
        }
        if (enemy.getY() + enemy.getYSpeed() >= Constants.MAX_HEIGHT) {
            enemy.setY(-50);
            enemy.setYSpeed(-Constants.PLAYER_DEFAULT_SPEED);
        }
    }

    // TODO: da controllare
    public void CollisioneEnemy(EnemyModel enemy) {
        for (PlatformModel platform : model.getPlatforms()) {
            if (enemy.collidesWith(platform)) {
                enemy.setColliding(false);
            } else {
                enemy.setColliding(true);
                enemy.setY(enemy.getY() + 1); // Gravità
            }
        }
    }
}
