package com.bubblebobble.views;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.bubblebobble.contansts.PowerUpType;
import com.bubblebobble.models.PowerUpModel;

public class PowerUpView extends JPanel {
    private PowerUpModel model;

    public PowerUpView(PowerUpModel model) {
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (model.is(PowerUpType.Speed)) {
            // --- CAMBIARE QUESTO CODICE CON L'IMMAGINE DEL POWERUP ---
            g.setColor(Color.BLUE);
            g.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
        }

        if (model.is(PowerUpType.Instakill)) {
            g.setColor(Color.green);
            g.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
        }

        if (model.is(PowerUpType.SuperJump)) {
            g.setColor(Color.red);
            g.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
        }

        if (model.is(PowerUpType.DoublePoints)) {
            g.setColor(Color.pink);
            g.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
        }

        if (model.is(PowerUpType.KillThemAll)) {
            g.setColor(Color.gray);
            g.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
        }

        if (model.is(PowerUpType.Freeze)) {
            g.setColor(Color.cyan);
            g.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
        }

        if (model.is(PowerUpType.FreezeAndKill)) {
            g.setColor(Color.MAGENTA);
            g.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
        }

        if (model.is(PowerUpType.JumpPoints)) {
            g.setColor(Color.BLUE);
            g.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
        }

        if (model.is(PowerUpType.FastShoot)) {
            g.setColor(Color.ORANGE);
            g.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
        }

        if (model.is(PowerUpType.Health)) {
            g.setColor(Color.RED);
            g.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
        }

        if (model.is(PowerUpType.Invincibility)) {
            g.setColor(Color.RED);
            g.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
        }

        if (model.is(PowerUpType.RandomPowerUp)) {
            g.setColor(Color.RED);
            g.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
        }
    }
}
