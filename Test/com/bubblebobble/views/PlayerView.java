package com.bubblebobble.views;

import com.bubblebobble.Constants;
import com.bubblebobble.controllers.GameController;
import com.bubblebobble.models.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

public class PlayerView extends JPanel { 

    GameController gc = new GameController(0);
    GameModel gm; 
    EnemyModel enemyModel;

    // Array di immagini per l'animazione della camminata
    // private int frameCount = 0; // Conteggio dei frame
    private ImageIcon[] walkFrames;
    private int currentFrame = 2; // Frame corrente dell'animazione
    private int totalframe = 3;

    private Image vita = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "vita.png");
    private Image gameOver = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "gameOver.png");

    private PlayerModel model;
    private Timer animationTimer;
    private int animationDelay = 200; // Time between frame changes in milliseconds

    public PlayerView(PlayerModel model) {
        this.model = model;

        // Carica le immagini dell'animazione della camminata
        walkFrames = new ImageIcon[totalframe]; // Supponiamo di avere 4 frame di animazione
        for (int i = 0; i < totalframe; i++) {
            walkFrames[i] = new ImageIcon(getClass().getResource("img/walk_" + i + ".png"));
        }

        // Initialize the timer
        animationTimer = new Timer(animationDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFrame();
            }
        });
        animationTimer.start();
    }

    private void updateFrame() {
        currentFrame = (currentFrame + 1) % totalframe; // Cycle through frames
        repaint(); // Repaint the component to show the new frame
    }

    public ImageIcon getCurrentWalkFrame() {
        return walkFrames[currentFrame];
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

    

    public void drawVita(Graphics g) {

        int xMassima = 50;

        for (int j = 1; j <= model.getVita(); j++) {
            g.drawImage(vita, xMassima, 50, null);
            xMassima += 20; // Stampa i cuori a 20 di distanza da destra a sinistra togliendo 20 dalla dist
                            // massima ad ogni ciclata
        }
        if (model.getVita() <= 0) {
            g.drawImage(gameOver, 50, 50, null);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Disegna l'immagine corrente dell'animazione della camminata del personaggio
        g.drawImage(getCurrentWalkFrame().getImage(), model.getX(), model.getY(), null);
        // Disegna i proiettili
        drawProjectiles(g);
    }
}
    