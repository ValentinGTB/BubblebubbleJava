package com.bubblebobble.controllers;

import com.bubblebobble.Constants;
import com.bubblebobble.models.*;
import com.bubblebobble.views.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameController {
    private boolean salta = false;
    private GameView game;
    private EnemyModel enemy;
    private PlayerModel player;
    private GameModel model;
    int xPlayer = 0;
    int yPlayer = 0;

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

        // piattagforme top e bottom

        platforms.add(new PlatformModel(25, Constants.MAX_HEIGHT * 90 / 100, Constants.MAX_WIDTH,
                Constants.ALL_PLATFORMHEIGHT));

        platforms.add(new PlatformModel(25, Constants.MAX_HEIGHT * 0 / 100, Constants.MAX_WIDTH,
                Constants.ALL_PLATFORMHEIGHT));


        // piattafroma
        
        platforms.add(new PlatformModel(500, Constants.MAX_HEIGHT * 75 / 100, Constants.MAX_WIDTH / 2,
                Constants.ALL_PLATFORMHEIGHT));

        enemy = new EnemyModel(player);
        model = new GameModel(player, platforms, enemy, walls);
        game = new GameView(model);
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
        enemy.move();

        BlocchiDirezzionali();
        ;

        xPlayer = player.getX();
        enemy.setPlayerX(xPlayer);

        yPlayer = player.getY();
        enemy.setPlayerY(yPlayer);

        ControlloSaltoPlatform();
        CollisioneEnemy();

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
        if (player.getX() + player.getXSpeed() < 0) {
            player.setXSpeed(0);
            player.setX(0);
        }

        if (player.getX() + player.getXSpeed() >= Constants.MAX_WIDTH - 50) {
            player.setXSpeed(0);
            player.setX(Constants.MAX_WIDTH - 50);
        }

        // Blocchi Enemy

    }

    public void onKeyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            player.setXSpeed(-Constants.SPEED);
        } else if (key == KeyEvent.VK_RIGHT) {
            player.setXSpeed(Constants.SPEED);
        } else if (key == KeyEvent.VK_UP) {
            player.salta();
        }
        // new EnemyModel();

    }

    public void onKeyReleased(KeyEvent e) {
        // TODO: evitare numeri magici.
        // usare constants, ma dentro player.
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            player.setXSpeed(0);
        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            player.setYSpeed(0);
        }
    }

}
