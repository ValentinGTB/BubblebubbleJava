package com.bubblebobble.controllers;

import com.bubblebobble.Constants;
import com.bubblebobble.levels.Level;
import com.bubblebobble.levels.Level01;
import com.bubblebobble.models.*;
import com.bubblebobble.views.*;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {
    private GameView game;
    private GameModel model;

    private static final int POWER_UP_SPEED_BOOST = 5; // Aumento della velocità con il power-up
    private boolean isPowerUpActive = false;
    boolean dead = false;
    int newY = 0; // TODO: da rimuovere.

    public GameController() {
        model = GameModel.getInstance();
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

    // eseguito ad ogni frame
    // qui dobbiamo sia aggiornare i modelli (es. fare il move) che fare il render
    // della view
    public void onTick() {

        updateProjectiles();

        for (EnemyModel enemy : model.getEnemies()) {

            // qui gestiamo ogni aggiornamento dei nostri modelli
            model.getPlayer().move();
            if (!enemy.isInBubble()) {
                enemy.move();
                newY = enemy.getY();
            } else {
                // Se il nemico è nella bolla, aggiorna solo la posizione verso l'alto
                if (newY >= 40 && !enemy.isFruit()) { // Se sto salendo (non sono in cima) e NON sono un frutto
                    newY -= 1;
                    enemy.setY(newY);
                    enemy.collisionDead();
                } else if (newY <= 40) { // Se sono in cima
                    dead = true; // Evita che arrivando in cima si attivi il CollisioneEnemy che ti farebbe
                                 // tornare in basso
                }
            }

            checkProjectileCollisions(enemy);
            BlocchiDirezzionali(enemy);
            ControlloSaltoPlatform();

            if (!dead || enemy.isFruit()) { // Se non sono morto oppure sono un frutto, riattiva la caduta
                CollisioneEnemy(enemy); // Fa ricominciare il nemico a cadere
            }

        }

        for (PowerUpModel pwupModel : model.getPowerUps().values()) {
            if (pwupModel.isExpired() && isPowerUpActive) {
                deactivatePowerUp();
            }

            checkPowerUpCollisions(pwupModel);
        }

    }

    private void checkPowerUpCollisions(PowerUpModel pwupModel) {
        if (!pwupModel.isActive() && model.getPlayer().getX() < pwupModel.getX() + pwupModel.getWidth() &&
                model.getPlayer().getX() + 40 > pwupModel.getX() &&
                model.getPlayer().getY() < pwupModel.getY() + pwupModel.getHeight() &&
                model.getPlayer().getY() + 40 > pwupModel.getY()) {
            pwupModel.activate();
            activatePowerUp();
            if (pwupModel == model.getPowerUps().get("killthemall"))
                Constants.killthemall = true;
            if (pwupModel == model.getPowerUps().get("freeze"))
                Constants.freeze = true;
            if (pwupModel == model.getPowerUps().get("freezeAndKill"))
                Constants.freezeAndKill = true;
        }
    }

    private void activatePowerUp() {
        isPowerUpActive = true;
    }

    private void deactivatePowerUp() {
        isPowerUpActive = false;
    }

    public void ControlloSaltoPlatform() {
        for (PlatformModel platform : model.getPlatforms()) {
            if (model.getPlayer().collidesWith(platform)) {
                model.getPlayer().setJumping(false);
            }
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
        if (model.getPlayer().getY() + model.getPlayer().getYSpeed() < 0) {
            model.getPlayer().setYSpeed(Constants.SPEED);
        }
        if (model.getPlayer().getY() + model.getPlayer().getYSpeed() >= Constants.MAX_HEIGHT) {
            model.getPlayer().setY(-40);
            model.getPlayer().setYSpeed(-Constants.SPEED);
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
        if (model.getPlayer().getX() + model.getPlayer().getXSpeed() < Constants.WallWidth) {
            System.err.println(Constants.WallWidth + "Wall width");
            model.getPlayer().setXSpeed(0);
            model.getPlayer().setX(Constants.WallWidth);
        }
        if (model.getPlayer().getX() + model.getPlayer().getXSpeed() >= Constants.MAX_WIDTH
                - (50 + Constants.WallWidth)) {
            model.getPlayer().setXSpeed(0);
            model.getPlayer().setX(Constants.MAX_WIDTH - (53 + Constants.WallWidth));
        }
    }

    private void checkProjectileCollisions(EnemyModel enemy) {
        List<ProjectileModel> projectiles = model.getProjectiles();
        for (ProjectileModel projectile : projectiles) {
            if (projectile.collidesWith(enemy)) {
                // Gestisci la collisione: ingloba il nemico nella bolla
                for (Map.Entry<String, PowerUpModel> entry : model.getPowerUps().entrySet()) {
                    String key = entry.getKey();
                    PowerUpModel valModel = (PowerUpModel) entry.getValue();

                    // Se il player NON ha raccolto il powerup INSTAKILL

                    if (key.equals("instakill")) {
                        if (!valModel.isActive())
                            enemy.setInBubble(true);
                        else {
                            enemy.instaKill();
                        }
                    }

                }

                projectile.deactivate();
            }

            else if (projectile.collidesWithWalls(Constants.WALL_LEFT, Constants.WALL_RIGHT) && projectile.isActive()) {
                int points = 10;

                if (model.hasPowerup("doppipunti"))
                    points *= 2;

                model.getScore().addPoints(points);
                projectile.deactivate();
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

    public void onKeyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        int currentSpeed = model.hasPowerup("velocita") ? Constants.BOOSTED_SPEED : Constants.SPEED;

        if (key == KeyEvent.VK_LEFT) {
            model.getPlayer().setXSpeed(-currentSpeed);
        } else if (key == KeyEvent.VK_RIGHT) {
            model.getPlayer().setXSpeed(currentSpeed);
        } else if (key == KeyEvent.VK_UP) {
            model.getPlayer().salta();
            if (model.getPowerUps().get("jumpPoints").isActive() && isPowerUpActive) {
                model.getScore().addPoints(100);
            }

        } else if (key == KeyEvent.VK_SPACE) {
            model.getPlayer().shoot();
        }
    }

    public void onKeyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            model.getPlayer().setXSpeed(0);
        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            model.getPlayer().setYSpeed(0);
        }
    }
}
