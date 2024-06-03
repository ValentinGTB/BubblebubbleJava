package com.bubblebobble.controllers;

import com.bubblebobble.Constants;
import com.bubblebobble.models.*;
import com.bubblebobble.views.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private boolean salta = false;
    private GameView game;
    private EnemyModel enemy;
    private PlayerModel player;
    private GameModel model;
    private ScoreModel scoreModel;
    boolean dead = false;
    int xPlayer = 0;
    int yPlayer = 0;
    int newY = 0;

    public GameController(int vuoto) {
    }

    public GameController() {
        ArrayList<PlatformModel> platforms = new ArrayList<>();
        ArrayList<WallModel> walls = new ArrayList<>();

        // Player
        player = new PlayerModel(Constants.MAX_WIDTH / 3,
                Constants.MAX_HEIGHT * 70 / 100 - Constants.ALL_PLATFORMHEIGHT);

        // muri esterni
        walls.add(new WallModel(0, 3, Constants.ALL_PLATFORMHEIGHT, Constants.MAX_WIDTH));
        walls.add(new WallModel(Constants.MAX_WIDTH - 55, 0, Constants.ALL_PLATFORMHEIGHT, Constants.MAX_WIDTH));

        // piattaforme top e bottom
        platforms.add(new PlatformModel(25, Constants.MAX_HEIGHT * 90 / 100, Constants.MAX_WIDTH,
                Constants.ALL_PLATFORMHEIGHT));
        platforms.add(new PlatformModel(25, Constants.MAX_HEIGHT * 0 / 100, Constants.MAX_WIDTH,
                Constants.ALL_PLATFORMHEIGHT));

        // piattaforma
        platforms.add(new PlatformModel(500, Constants.MAX_HEIGHT * 75 / 100, Constants.MAX_WIDTH / 2,
                Constants.ALL_PLATFORMHEIGHT));
        platforms.add(new PlatformModel(100, Constants.MAX_HEIGHT * 75 / 100, 0,
                Constants.ALL_PLATFORMHEIGHT));

        enemy = new EnemyModel(player, walls);
        model = new GameModel(player, platforms, enemy, walls);
        scoreModel = new ScoreModel();
        game = new GameView(model, scoreModel);
    }

    public GameView getGame() {
        return game;
    }

    public GameModel getGameModel() {
        return model;
    }

    // eseguito ad ogni frame
    // qui dobbiamo sia aggiornare i modelli (es. fare il move) che fare il render
    // della view
    public void onTick() {
        // qui gestiamo ogni aggiornamento dei nostri modelli
        player.move();
        if (!enemy.isInBubble()) {
            enemy.move();
            newY = enemy.getEnemyY();
        } else {
            // Se il nemico è nella bolla, aggiorna solo la posizione verso l'alto
            if (newY >= 40 && !enemy.isFruit()) { // Se sto salendo (non sono in cima) e NON sono un frutto
                newY -= 1;
                enemy.setEnemyY(newY);
                enemy.collisionDead();
            } else if (newY <= 40) { // Se sono in cima
                dead = true; // Evita che arrivando in cima si attivi il CollisioneEnemy che ti farebbe tornare in basso
            }
        }

        checkProjectileCollisions();
        BlocchiDirezzionali();

        xPlayer = player.getX();
        enemy.setPlayerX(xPlayer);

        yPlayer = player.getY();
        enemy.setPlayerY(yPlayer);

        ControlloSaltoPlatform();

        if (!dead || enemy.isFruit()) { // Se non sono morto oppure sono un frutto, riattiva la caduta
            CollisioneEnemy(); // Fa ricominciare il nemico a cadere
        }

    }

    public void ControlloSaltoPlatform() {
        for (PlatformModel platform : model.getPlatforms()) {
            if (player.collidesWith(platform)) {
                player.setJumping(false);
            }
        }
    }

    public void CollisioneEnemy() {
        for (PlatformModel platform : model.getPlatforms()) {
            if (enemy.collidesWith(platform)) {
                enemy.setColliding(false);
            } else {
                enemy.setColliding(true);
                enemy.setEnemyY(enemy.getEnemyY() + 1); // Gravità
            }
        }
    }

    public void BlocchiDirezzionali() {
        blocchiBordiLeftRight();
        blocchiBordiTopBottom();
    }

    public void blocchiBordiTopBottom() {
        if (player.getY() + player.getYSpeed() < 0) {
            player.setYSpeed(Constants.SPEED);
        }
        if (player.getY() + player.getYSpeed() >= Constants.MAX_HEIGHT) {
            player.setY(-40);
            player.setYSpeed(-Constants.SPEED);
        }

        // Blocco Enemy
        if (enemy.getEnemyY() + enemy.getEnemySpeed() < 0) {
            enemy.setEnemySpeed(Constants.SPEED);
            System.out.println("enemyY < enemySpeed");
        }
        if (enemy.getEnemyY() + enemy.getEnemySpeed() >= Constants.MAX_HEIGHT) {
            enemy.setEnemyY(-50);
            enemy.setEnemySpeed(-Constants.SPEED);
            System.out.println("enemyY > max height");
        }
    }

    public void blocchiBordiLeftRight() {
        if (player.getX() + player.getXSpeed() < Constants.WallWidth) {
            System.err.println(Constants.WallWidth + "Wall width");
            player.setXSpeed(0);
            player.setX(Constants.WallWidth);
        }
        if (player.getX() + player.getXSpeed() >= Constants.MAX_WIDTH - (50 + Constants.WallWidth)) {
            player.setXSpeed(0);
            player.setX(Constants.MAX_WIDTH - (53 + Constants.WallWidth));
        }
    }

    private void checkProjectileCollisions() {
        List<ProjectileModel> projectiles = player.getProjectiles();
        for (ProjectileModel projectile : projectiles) {
            if (projectile.collidesWith(enemy)) {
                // Gestisci la collisione: ingloba il nemico nella bolla
                enemy.setInBubble(true);
                projectile.setVisible(false);  // Nascondi il proiettile
                scoreModel.addPoints(100); // Aggiungi punti quando il nemico viene colpito
            }
        }
    }

    public void onKeyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            player.setXSpeed(-Constants.SPEED);
        } else if (key == KeyEvent.VK_RIGHT) {
            player.setXSpeed(Constants.SPEED);
        } else if (key == KeyEvent.VK_UP) {
            player.salta();
        } else if (key == KeyEvent.VK_SPACE) {
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
