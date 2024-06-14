package com.bubblebobble.views;

import com.bubblebobble.Constants;
import com.bubblebobble.ResourceManager;
import com.bubblebobble.contansts.Direction;
import com.bubblebobble.models.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;
/**
 * Questa classe rappresenta la visualizzazione del nemico.
 * Gestisce l'animazione, il disegno del nemico, e la gestione dello stato del nemico (in bolla, frutta...).
 */
public class EnemyView extends JComponent {
    private EnemyModel model;

    private Image currentFruitImage;
    private Image bubbleImage = ResourceManager.getInstance().getImage("pallanemica.png");

    private Timer animationTimer;
    private int currentFrame = 0;
    private int totalFrames = 3;
    private int animationDelay = 200;
    /**
     * Costruttore di EnemyView.
     * Inizializza la visualizzazoine del nemico con il modello specificato.
     *
     * @param enemy Il modello del nemico da visualizzare.
     */
    public EnemyView(EnemyModel enemy) {
        this.model = enemy;

        animationTimer = new Timer(animationDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFrame();
            }
        });

        animationTimer.start();
    }
    /**
     * Aggiorna il frame corrente dell'animazione e richiede la ridisegnazione del componente.
     */
    private void updateFrame() {
        currentFrame = (currentFrame + 1) % totalFrames; // Cambia il frame
        repaint(); // Ridisegna il componente
    }
    /**
     * Ottiene l'immagine della frutta corrispondente all'ID specificato.
     *
     * @param fruitId L'ID della frutta.
     * @return L'immagine della frutta.
     */
    private Image getFruitImage(int fruitId) {
        return ResourceManager.getInstance().getImage("Frutta/Cibo-" + fruitId + ".png");
    }
    /**
     * Seleziona casualmente l'immagine di un frutto da mostrare quando il nemico viene trasformato in frutta.
     */
    private void selectRandomFruit() {
        Random random = new Random();
        currentFruitImage = getFruitImage(1 + random.nextInt(29));
    }
    /**
     * Ottiene l'immagine appropriata per il tipo di nemico.
     *
     * @return L'immagine del nemico.
     */
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
        } else if (model instanceof HideonEnemyModel) {
            return resources.getImage("Hideon.png");
        }else {
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
