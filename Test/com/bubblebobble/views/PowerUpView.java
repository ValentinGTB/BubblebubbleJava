package com.bubblebobble.views;

import com.bubblebobble.ResourceManager;
import com.bubblebobble.models.PowerUpModel;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
/**
 * Questa classe rappresenta la visualizzazione di un power-up nel gioco.
 * Estende JPanel e utilizza {@code PowerUpModel} per ottenere le informazioni necessarie per il rendering.
 */
public class PowerUpView extends JPanel {
    private PowerUpModel model;

    public PowerUpView(PowerUpModel model) {
        this.model = model;
    }

    private Image getPowerUpImage() {
        return ResourceManager.getInstance().getImage("PowerUps/" + model.getPowerUpType().toString() + ".png");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image image = getPowerUpImage();
        g.drawImage(image, model.getX(), model.getY(), model.getWidth(), model.getHeight(), this);
    }
}
