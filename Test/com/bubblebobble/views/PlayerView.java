package com.bubblebobble.views;

import com.bubblebobble.ResourceManager;
import com.bubblebobble.contansts.Direction;
import com.bubblebobble.contansts.PowerUpType;
import com.bubblebobble.models.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class PlayerView extends JPanel {

    GameModel gm;
    EnemyModel enemyModel;

    // Array di immagini per l'animazione della camminata
    private int currentFrame = 2; // Frame corrente dell'animazione
    private int totalframe = 3;

    private Image hearthImage = ResourceManager.getInstance().getImage("vita.png");
    private Image gameOverImage = ResourceManager.getInstance().getImage("gameOver.png");

    private PlayerModel model;
    private Timer animationTimer;
    private int animationDelay = 250; // Time between frame changes in milliseconds

    public PlayerView(PlayerModel model) {
        this.model = model;

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

    public Image getWalkFrame(int frame) {
        return ResourceManager.getInstance().getImage("walk_" + frame + ".png");
    }

    public Image getCurrentWalkFrame() {
        return getWalkFrame(currentFrame);
    }

    public void drawVita(Graphics g) {
        int xMassima = 50;

        for (int j = 1; j <= model.getLives(); j++) {
            g.drawImage(hearthImage, xMassima, 50, null);
            xMassima += 20; // Stampa i cuori a 20 di distanza da destra a sinistra togliendo 20 dalla dist
                            // massima ad ogni ciclata
        }
        if (model.getLives() <= 0) {
            g.drawImage(gameOverImage, 50, 50, null);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Disegna l'immagine corrente dell'animazione della camminata del personaggio
        Image image = null;

        if (GameModel.getInstance().hasPowerup(PowerUpType.Invincibility)) {
            float alpha = System.currentTimeMillis() % 5 == 0 ? 0.6f : 1f;

            Image frame = getCurrentWalkFrame();
            BufferedImage bufferedImage = new BufferedImage(model.getWidth(), model.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.drawImage(frame, 0, 0, model.getWidth(), model.getHeight(), null);
            g2d.dispose();

            image = bufferedImage;
        } else {
            image = getCurrentWalkFrame();
        }

        var delta = model.getDirection() == Direction.LEFT ? 1 : -1;
        g.drawImage(image, model.getX() + (delta < 0 ? model.getWidth() : 0), model.getY(),
                model.getWidth() * delta, model.getHeight(), null);
        drawVita(g);
    }
}
