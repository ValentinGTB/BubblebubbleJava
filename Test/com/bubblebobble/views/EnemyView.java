package com.bubblebobble.views;

import com.bubblebobble.Constants;
import com.bubblebobble.ResourceManager;
import com.bubblebobble.contansts.Direction;
import com.bubblebobble.models.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class EnemyView extends JComponent {
    private EnemyModel model;

    private Image enemyImage;
    private Image bubbleImage;
    private Image[] fruitImages;
    private Image currentFruitImage;

    private Timer animationTimer;
    private int currentFrame = 0;
    private int totalFrames = 3;
    private int animationDelay = 200;

    public EnemyView(EnemyModel enemy) {
        this.bubbleImage = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "pallanemica.png");
        this.enemyImage = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "Enemy1.png");
        this.model = enemy;

        loadFruitImages();
        loadWalkFrames();
    }

    private void loadWalkFrames() {
        animationTimer = new Timer(animationDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFrame();
            }
        });

        animationTimer.start();
    }

    private void updateFrame() {
        currentFrame = (currentFrame + 1) % totalFrames; // Cambia il frame
        repaint(); // Ridisegna il componente
    }

    private void loadFruitImages() {
        fruitImages = new Image[30];
        for (int i = 0; i < 29; i++) {
            fruitImages[i] = Toolkit.getDefaultToolkit()
                    .getImage(Constants.BaseURL + "/Frutta/Cibo-" + (i + 1) + ".png");
        }
    }

    private void selectRandomFruit() {
        Random random = new Random();
        int randomIndex = random.nextInt(fruitImages.length);
        currentFruitImage = fruitImages[randomIndex];
    }

    private Image getImage() {
        ResourceManager resources = ResourceManager.getInstance();
        if (model instanceof WizardEnemeyModel) {
            return resources.getImage("Wizard.png");
        } else if (model instanceof GhostEnemyModel) {
            return resources.getImage("Ghost.png");
        } else if (model instanceof FastEnemyModel) {
            return resources.getImage("Fast.png");
        } else if (model instanceof DrunkEnemyModel) {
            return resources.getImage("Drunk.png");
        } else {
            return resources.getImage("Nemico_" + currentFrame + ".png");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int enemyX = model.getX();
        int enemyY = model.getY();

        if (model.isInBubble()) {
            g.drawImage(bubbleImage, enemyX, enemyY, Constants.PLATFORM_HEIGHT, Constants.PLATFORM_HEIGHT, this);
        } else if (model.isFruit()) {
            if (currentFruitImage == null) {
                selectRandomFruit();
            }
            g.setColor(Color.black);
            g.drawImage(currentFruitImage, enemyX, enemyY, Constants.PLATFORM_HEIGHT, Constants.PLATFORM_HEIGHT,
                    this);
        } else {
            var delta = model.getDirection() == Direction.LEFT ? 1 : -1;
            g.drawImage(getImage(), enemyX + (delta < 0 ? model.getWidth() : 0), enemyY, model.getWidth() * delta, model.getHeight(), this);
        }
    }
}
