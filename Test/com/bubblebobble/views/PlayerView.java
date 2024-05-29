package com.bubblebobble.views;

import com.bubblebobble.Constants;
import com.bubblebobble.controllers.GameController;
import com.bubblebobble.models.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class PlayerView {
    
    GameController gc = new GameController(0);
    GameModel gm; 
    EnemyModel enemyModel;

    // Array di immagini per l'animazione della camminata
    // private int frameCount = 0; // Conteggio dei frame
    private ImageIcon[] walkFrames;
    private int currentFrame = 0; // Frame corrente dell'animazione
    private int totalframe = 4;

    private Image vita = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "vita.png");
    private Image gameOver = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "gameOver.png");

    private PlayerModel model;

    public PlayerView(PlayerModel model) {
        this.model = model;

        // Carica le immagini dell'animazione della camminata
        walkFrames = new ImageIcon[totalframe]; // Supponiamo di avere 4 frame di animazione
        for (int i = 0; i < totalframe; i++) {
            walkFrames[i] = new ImageIcon(getClass().getResource("img/walk_" + i + ".png"));
        }
    }

        private Image projectileImage = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "proiettile.png");
    
        public void drawProjectiles(Graphics g) {
            List<ProjectileModel> projectiles = model.getProjectiles();
            for (ProjectileModel projectile : projectiles) {
                if (projectile.isActive()) {
                    g.drawImage(projectileImage, projectile.getX(), projectile.getY(), null);
                }
            }
        }

    public ImageIcon getCurrentWalkFrame() {
        return walkFrames[currentFrame];
    }

    public void drawVita(Graphics g) {
        // Disegna vita
        int xMassima = 50;

        // Stampa j volte l'immagine fino ad arrivare alla vitaAttuale // La vitaAttuale
        // viene decrementata per cui il for arriverà sempre a meno

        for (int j = 1; j <= model.getVita(); j++) {
            g.drawImage(vita, xMassima, 50, null);
            xMassima += 20; // Stampa i cuori a 20 di distanza da destra a sinistra togliendo 20 dalla dist
                            // massima ad ogni ciclata
        }
        if (model.getVita() <= 0) {
            g.drawImage(gameOver, 50, 50, null);
        }
    }

    public void paintComponent(Graphics g) {
        // Disegna l'immagine corrente dell'animazione della camminata del personaggio
            g.drawImage(getCurrentWalkFrame().getImage(), model.getX(), model.getY(), null);
            // Disegna i proiettili
            drawProjectiles(g);
    }
}
