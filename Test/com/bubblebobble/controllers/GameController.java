package com.bubblebobble.controllers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import com.bubblebobble.views.*;
import com.bubblebobble.Constants;
import com.bubblebobble.models.*;

public class GameController {
    private GameView game;

    private PlayerModel player;
    private GameModel model;

    public GameController() {
        player = new PlayerModel(Constants.MAX_WIDTH/2, Constants.MAX_HEIGHT * 70 / 100);
        
        ArrayList<PlatformModel> platforms = new ArrayList<>();
        platforms.add(new PlatformModel(0, Constants.MAX_HEIGHT*90/100, Constants.MAX_WIDTH, Constants.ALL_PLATFORMHEIGHT));
        platforms.add(new PlatformModel(200, Constants.MAX_HEIGHT*70/100, Constants.MAX_WIDTH/2, Constants.ALL_PLATFORMHEIGHT));

        model = new GameModel(player, platforms);
        game = new GameView(model);
    }

    
    /** 
     * @return GameView
     */
    public GameView getGame() {
        return game;
    }

    // eseguito ad ogni frame
    // qui dobbiamo sia aggiornare i modelli (es. fare il move) che fare il render della view  
    public void onTick() {
        // qui gestiamo ogni aggiornamento dei nostri modelli
        
        player.move();

            // Se il player si sposta troppo a sinistra, blocchiamolo sul bordo sinistro dello schermo
        if (player.getX() + player.getXSpeed() < 0) {
            player.setX(0);
        }

        // Se il player si sposta troppo a destra, blocchiamolo sul bordo destro dello schermo
        if (player.getX() + player.getXSpeed() >= Constants.MAX_WIDTH) {
            player.setX(Constants.MAX_WIDTH);
        }

        // Se il player si sposta troppo in alto, blocchiamolo sul bordo superiore dello schermo
        if (player.getY() + player.getYSpeed() < 0) {
            player.setY(0);
        }

        // Controllo della collisione con la piattaforma solo se il personaggio sta scendendo
        // Se il player si trova troppo in basso e sta scendendo, blocchiamolo sopra la piattaforma
        if (player.getY() + player.getYSpeed() >= Constants.MAX_HEIGHT && player.getYSpeed() > 0) {
            player.setY(Constants.MAX_HEIGHT);
        }

        // Se il player si sposta troppo in basso, riportalo sopra il bordo superiore dello schermo
        if (player.getY() + player.getYSpeed() >= Constants.MAX_HEIGHT) {
            player.setY(-Constants.MAX_HEIGHT);
        }

        for (PlatformModel platform : model.getPlatforms()) {
            if (player.collidesWith(platform)) {
                player.setJumping(false);
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
        }
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
