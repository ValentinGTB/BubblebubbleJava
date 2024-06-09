package com.bubblebobble.controllers;

import com.bubblebobble.Constants;
import com.bubblebobble.levels.Level;
import com.bubblebobble.levels.Level01;
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

public class GameController {
    private GameView game;
    private GameModel model;
    private PlayerModel player;

    private static final int POWER_UP_SPEED_BOOST = 5; // Aumento della velocità con il power-up
    boolean dead = false;

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
        changeLevel(new Level01());
    }

    public void changeLevel(Level level) {
        model.loadLevel(level);
        game.onChangeLevel();
    }

    private void updatePowerUps() {
        // remove expired powerups
        for (Map.Entry<String, PowerUpModel> entry : model.getPowerUps().entrySet()) {
            if (entry.getValue().isExpired()) {
                model.removePowerUp(entry.getKey());
                return;
            }
        }

        // check power up collisions
        for (PowerUpModel pwupModel : model.getPowerUps().values()) {
            checkPowerUpCollisions(pwupModel);
        }
    }

    private void updateEnemies() {

        ArrayList<EnemyModel> destroyedEnemies = new ArrayList<>();
        for (EnemyModel enemy : model.getEnemies()) {
            enemy.move();

            // Se il nemico è nella bolla, aggiorna solo la posizione verso l'alto
            if (enemy.isInBubble() && enemy.getY() >= 40 && !enemy.isFruit()) { // Se sto salendo (non sono in cima) e
                                                                                // NON sono un frutto
                enemy.moveY(-1);
            } else if (enemy.getY() <= 40) { // Se sono in cima
                dead = true; // Evita che arrivando in cima si attivi il CollisioneEnemy che ti farebbe
                // tornare in basso
            }

            if (player.collidesWith(enemy)) {
                // se il nemico è vivo, il giocatore perde la vita
                if (!enemy.isFruit() && !enemy.isInBubble()) {
                    player.decreaseLife();
                    player.setX(Constants.MAX_WIDTH / 3);
                    player.setY(Constants.MAX_HEIGHT * 70 / 100 - Constants.ALL_PLATFORMHEIGHT);
                }

                // se il nemico è in una bolla ma non è un frutto, divennta un frutto
                else if (enemy.isInBubble() && !enemy.isFruit()) {
                    enemy.setFruit(true);
                }

                // se non è una bolla ma è un frutto, facciamo sparire il nemico e guadagniamo
                // dei punti
                else if (!enemy.isInBubble() && enemy.isFruit()) {
                    System.out.println("MANGIATO");
                    Constants.livelloAttuale = 1;
                    enemy.setEaten(true);
                    destroyedEnemies.add(enemy);
                    model.getScore().addPoints(100);
                }
            }

            checkProjectileCollisions(enemy);
            BlocchiDirezzionali(enemy);

            if (!dead || enemy.isFruit()) { // Se non sono morto oppure sono un frutto,
                // riattiva la caduta
                // CollisioneEnemy(enemy); // Fa ricominciare il nemico a cadere
            }
        }

        // rimuovi nemici che sono stati mangiati
        // model
        // .getEnemies().stream()
        // .filter(EnemyModel::isEaten)
        // .forEach(enemey -> model.removeEnemy(enemey));

        boolean removeEnemy = destroyedEnemies.size() > 0;
        for (EnemyModel enemyModel : destroyedEnemies) {
            model.removeEnemy(enemyModel);
        }

        if (removeEnemy)
            model.notify("removeEnemy", new ActionEvent(this, 0, "updateEnemies"));
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
            System.out.println("Level completed.");
            changeLevel(new Level01());
        }
    }

    private void checkPowerUpCollisions(PowerUpModel pwup) {
        if (!pwup.isActive() && pwup.collidesWith(player)) {
            pwup.activate();
            if (pwup == model.getPowerUps().get("killthemall"))
                Constants.killthemall = true;
            if (pwup == model.getPowerUps().get("freeze"))
                Constants.freeze = true;
            if (pwup == model.getPowerUps().get("freezeAndKill"))
                Constants.freezeAndKill = true;
        }
    }

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

    public void BlocchiDirezzionali(EnemyModel enemy) {
        blocchiBordiLeftRight();
        blocchiBordiTopBottom(enemy);
    }

    public void blocchiBordiTopBottom(EnemyModel enemy) {
        if (player.getY() + player.getYSpeed() < 0) {
            player.setYSpeed(Constants.SPEED);
        }
        if (player.getY() + player.getYSpeed() >= Constants.MAX_HEIGHT) {
            player.setY(-40);
            player.setYSpeed(-Constants.SPEED);
        }

        // Blocco Enemy
        if (enemy.getY() + enemy.getEnemySpeed() < 0) {
            enemy.setEnemySpeed(Constants.SPEED);
        }
        if (enemy.getY() + enemy.getEnemySpeed() >= Constants.MAX_HEIGHT) {
            enemy.setY(-50);
            enemy.setEnemySpeed(-Constants.SPEED);
        }
    }

    public void blocchiBordiLeftRight() {
        if (player.getX() + player.getXSpeed() < Constants.WallWidth) {
            System.err.println(Constants.WallWidth + "Wall width");
            player.setXSpeed(0);
            player.setX(Constants.WallWidth);
        }
        if (player.getX() + player.getXSpeed() >= Constants.MAX_WIDTH
                - (50 + Constants.WallWidth)) {
            player.setXSpeed(0);
            player.setX(Constants.MAX_WIDTH - (53 + Constants.WallWidth));
        }
    }

    private void checkProjectileCollisions(EnemyModel enemy) {
        List<ProjectileModel> projectiles = model.getProjectiles();
        for (ProjectileModel projectile : projectiles) {
            if (projectile.collidesWith(enemy)) {

                if (model.hasPowerup("instakill")) {
                    enemy.instaKill();
                } else {
                    enemy.kill();
                }

                projectile.deactivate();
            }

            else if (projectile.isActive()
                    && model.getWalls().stream().anyMatch(wall -> projectile.collidesWith(wall))) {
                model.getScore().addPoints(10);
                projectile.deactivate();
            }
        }
    }

    private void updatePlayer() {
        player.move();
    }

    private void checkGravity() {
        // Regola la posizione del personaggio per farlo rimanere sulla piattaforma
        ArrayList<CharacterModel> entities = new ArrayList<>();
        entities.addAll(model.getEnemies());
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
        long shootDelay = model.hasPowerup("fastshoot") ? Constants.SHORT_SHOOT_DELAY : Constants.SHOOT_DELAY;

        return timePassed >= shootDelay;
    }

    public void onKeyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        int currentSpeed = model.hasPowerup("velocita") ? Constants.BOOSTED_SPEED : Constants.SPEED;

        if (key == KeyEvent.VK_LEFT) {
            player.setXSpeed(-currentSpeed);
        } else if (key == KeyEvent.VK_RIGHT) {
            player.setXSpeed(currentSpeed);
        } else if (key == KeyEvent.VK_UP) {
            player.jump(model.hasPowerup("superjump") ? 25 : 20);
            if (model.hasPowerup("jumpPoints")) {
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
}
