package com.bubblebobble.views;

import com.bubblebobble.Constants;
import com.bubblebobble.models.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class EnemyView extends JComponent {
    private Image enemyImage;
    private Image bubbleImage;
    private Image[] fruitImages;
    private Image currentFruitImage;
    private Image Enemy1 = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "Enemy1.png");

    ArrayList<EnemyModel> enemyArray;

    private Image[] walkFrames;
    private Timer animationTimer;
    private int currentFrame = 0;
    private int totalFrames = 3;
    private int animationDelay = 200;

    public EnemyView(ArrayList<EnemyModel> enemyArray) {
        this.enemyImage = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "Enemy1.png");
        this.bubbleImage = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "pallanemica.png");
        this.enemyArray = enemyArray;
        loadFruitImages();

        for(EnemyModel enemy : enemyArray)
        {

            walkFrames = new Image[totalFrames];
            for (int i = 0; i < totalFrames; i++) {
                walkFrames[i] = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "Nemico_" + i + ".png");
            }

            animationTimer = new Timer(animationDelay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateFrame();
                }
            });
            animationTimer.start(); // Avvia il timer
        }
    }
    
    private void updateFrame() {
        currentFrame = (currentFrame + 1) % totalFrames; // Cambia il frame
        repaint(); // Ridisegna il componente
    }

    // FRUTTA CASUALE

    private void loadFruitImages() {
        fruitImages = new Image[30];
        for (int i = 0; i < 29; i++) {
            fruitImages[i] = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "/Frutta/Cibo-" + (i + 1) + ".png");
        }
    }

    private void selectRandomFruit() {
        Random random = new Random();
        int randomIndex = random.nextInt(fruitImages.length);
        currentFruitImage = fruitImages[randomIndex];
    }

    //


    @Override
    public void paintComponent(Graphics g) {

        for(EnemyModel enemy: enemyArray)
        {

            int enemyX = enemy.getX();
            int enemyY = enemy.getY();
            super.paintComponent(g);

            g.drawImage(Enemy1, enemyX , enemyY, null);
            
            if (enemy.isInBubble() && !enemy.isFruit()) {
                g.drawImage(bubbleImage, enemyX, enemyY, Constants.ALL_PLATFORMHEIGHT, Constants.ALL_PLATFORMHEIGHT, this);
            } else if (enemy.isInBubble() && enemy.isFruit()) {
                if (currentFruitImage == null) {
                    selectRandomFruit();
                }
                g.setColor(Color.black);
                g.fillRect(enemyX, enemyY, Constants.ALL_PLATFORMHEIGHT, Constants.ALL_PLATFORMHEIGHT);
                g.drawImage(currentFruitImage, enemyX, enemyY, Constants.ALL_PLATFORMHEIGHT, Constants.ALL_PLATFORMHEIGHT, this);
            } else if (!enemy.isInBubble()) {
                g.drawImage(enemyImage, enemyX, enemyY, Constants.ALL_PLATFORMHEIGHT, Constants.ALL_PLATFORMHEIGHT, this);
            } 
            
            if (!enemy.isFruit() && !enemy.isInBubble()) { //Se è vivo normalmente
                g.drawImage(walkFrames[currentFrame], enemyX, enemyY, Constants.ALL_PLATFORMHEIGHT, Constants.ALL_PLATFORMHEIGHT, this);
            }

            if (enemy.isEaten() && !enemy.getColliding()) {
                g.setColor(Color.white);
                g.fillRect(enemyX, enemyY, Constants.ALL_PLATFORMHEIGHT, Constants.ALL_PLATFORMHEIGHT);
            }
        }
    }
}
