// File: com/bubblebobble/views/GameView.java
package com.bubblebobble.views;

import com.bubblebobble.Constants;
import com.bubblebobble.ResourceManager;
import com.bubblebobble.contansts.PowerUpType;
import com.bubblebobble.models.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

public class GameView extends JPanel {
    private GameModel game;
    private PlayerView player;

    private List<WallView> walls;
    private List<EnemyView> enemies;
    private List<PowerUpView> powerUps;
    private List<PlatformView> platforms;

    private Image projectileImage = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "proiettile.png");

    public GameView(GameModel model) {
        game = model;
        player = new PlayerView(model.getPlayer());
        setupNotificationn();
    }

    private void setupNotificationn() {
        game.addSubscriber("removeEnemy", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enemies = game.getEnemies().stream().map(EnemyView::new).toList();
            }
        });

        game.addSubscriber("collectPowerUp", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                powerUps = game.getPowerUps().stream().map(PowerUpView::new).toList();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!Constants.isGamePaused) {
            drawWalls(g);
            drawPlatforms(g);
            drawPowerUps(g);
            drawScore(g);

            drawEnemies(g);
            drawPlayer(g);

            drawProjectiles(g);
        }
        // else {
        //     g.setColor(Color.white);
        //     g.fillRect(0, 0, Constants.MAX_WIDTH, Constants.MAX_HEIGHT);
        // }

        // Disegna il power-up
        // if (powerUp.isActive()) {
        // g.setColor(Color.BLACK);
        // g.fillRect(powerUp.getX(), powerUp.getY(), powerUp.getWidth(),
        // powerUp.getHeight());
        // }
    }

    // TODO: creare un evento onChangeLevel, che aggiorna le proprietà "walls",
    // "platforms", etc. e ricrea le view.
    public void onChangeLevel() {
        update();
    }

    private void update() {
        // ricrea le view
        walls = game.getWalls().stream().map(WallView::new).toList();
        enemies = game.getEnemies().stream().map(EnemyView::new).toList();
        powerUps = game.getPowerUps().stream().map(PowerUpView::new).toList();
        platforms = game.getPlatforms().stream().map(PlatformView::new).toList();
    }

    private void drawWalls(Graphics g) {
        walls.forEach(wall -> wall.paintComponent(g));
    }

    private void drawPlatforms(Graphics g) {
        // disegna piattaforme
        platforms.forEach(platform -> platform.paintComponent(g));
    }

    private void drawPlayer(Graphics g) {
        // disegna personaggio
        player.paintComponent(g);
    }

    public void drawProjectiles(Graphics g) {
        List<ProjectileModel> projectiles = game.getProjectiles();
        for (ProjectileModel projectile : projectiles) {
            if (projectile.isActive()) {
                g.drawImage(projectileImage, projectile.getX(), projectile.getY(), projectile.getWidth(), projectile.getHeight(), null);
            }
        }
    }

    private void drawEnemies(Graphics g) {
        // Disegna nemico
        enemies.forEach(enemy -> enemy.paintComponent(g));
    }

    private void drawPowerUps(Graphics g) {
        // Disegna PowerUp
        powerUps.forEach(pwup -> pwup.paintComponent(g));
    }

    private void drawScore(Graphics g) {
        g.setFont(ResourceManager.getInstance().getFontOrDefault(Constants.FONT_NAME));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + game.getScore().getCurrentScore(), 180, 65);
    }
}
