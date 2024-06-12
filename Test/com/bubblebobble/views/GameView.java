// File: com/bubblebobble/views/GameView.java
package com.bubblebobble.views;

import com.bubblebobble.Constants;
import com.bubblebobble.ResourceManager;
import com.bubblebobble.contansts.Events;
import com.bubblebobble.models.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

public class GameView extends JPanel {
    private GameModel game;
    private PlayerView player;

    private List<WallView> walls;
    private List<EnemyView> enemies;
    private List<PowerUpView> powerUps;
    private List<PlatformView> platforms;

    public GameView(GameModel model) {
        game = model;
        player = new PlayerView(model.getPlayer());
        setBackground(Color.BLACK);
        setupNotificationn();
    }

    private void setupNotificationn() {
        game.addSubscriber(Events.REMOVE_ENEMY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enemies = game.getEnemies().stream().map(EnemyView::new).toList();
            }
        });

        game.addSubscriber(Events.COLLECT_POWERUP, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                powerUps = game.getPowerUps().stream().map(PowerUpView::new).toList();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawWalls(g);
        drawPlatforms(g);
        drawPowerUps(g);
        drawScore(g);

        drawEnemies(g);
        drawPlayer(g);

        drawProjectiles(g);
    }

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
                Image image = ResourceManager.getInstance().getImage(projectile.getName() + ".png");
                g.drawImage(image, projectile.getX(), projectile.getY(), projectile.getWidth(), projectile.getHeight(), null);
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
