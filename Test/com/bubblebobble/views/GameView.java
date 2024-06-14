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
/**
 * La classe {@code GameView} gestisce la visualizzazione del gioco.
 * Collegata ad altre classi {@code view} si occupa di richiamare il metodo
 * {@code paintComponent()} di tutti gli elementi.
 * Estende {@code JPanel} e si occupa di disegnare i vari elementi
 * come muri, nemici, giocatore...
 */
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
    /**
     * Aggiunge listener per l'eliminazione dei nemici e quando vengono raccolti i power-up
     */
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
    /**
     * Se il gioco non è in pausa, disegna i vari componenti richiamando i metodi necessari
     */
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
    /**
     * Ricrea le view per elementi "di base" del gioco come muri, nemici, power-up, piattaforme.
     */
    private void update() {
        // ricrea le view
        walls = game.getWalls().stream().map(WallView::new).toList();
        enemies = game.getEnemies().stream().map(EnemyView::new).toList();
        powerUps = game.getPowerUps().stream().map(PowerUpView::new).toList();
        platforms = game.getPlatforms().stream().map(PlatformView::new).toList();
    }
    /**
     * Disegna i muri
     *
     * @param g il contesto grafico in cui disegnare
     */
    private void drawWalls(Graphics g) {
        walls.forEach(wall -> wall.paintComponent(g));
    }
    /**
     * Disegna le piattaforme
     *
     * @param g il contesto grafico in cui disegnare
     */
    private void drawPlatforms(Graphics g) {
        // disegna piattaforme
        platforms.forEach(platform -> platform.paintComponent(g));
    }
     /**
     * Disegna il giocatore
     *
     * @param g il contesto grafico in cui disegnare
     */
    private void drawPlayer(Graphics g) {
        // disegna personaggio
        player.paintComponent(g);
    }
    /**
     * Disegna i proiettili
     *
     * @param g il contesto grafico in cui disegnare
     */
    public void drawProjectiles(Graphics g) {
        List<ProjectileModel> projectiles = game.getProjectiles();
        for (ProjectileModel projectile : projectiles) {
            if (projectile.isActive()) {
                Image image = ResourceManager.getInstance().getImage(projectile.getName() + ".png");
                g.drawImage(image, projectile.getX(), projectile.getY(), projectile.getWidth(), projectile.getHeight(), null);
            }
        }
    }
    /**
     * Disegna i nemici
     *
     * @param g il contesto grafico in cui disegnare
     */
    private void drawEnemies(Graphics g) {
        // Disegna nemico
        enemies.forEach(enemy -> enemy.paintComponent(g));
    }
    /**
     * Disegna i power-up
     *
     * @param g il contesto grafico in cui disegnare
     */
    private void drawPowerUps(Graphics g) {
        // Disegna PowerUp
        powerUps.forEach(pwup -> pwup.paintComponent(g));
    }
     /**
     * Disegna il punteggio
     *
     * @param g il contesto grafico in cui disegnare
     */
    private void drawScore(Graphics g) {
        g.setFont(ResourceManager.getInstance().getFontOrDefault(Constants.FONT_NAME));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + game.getScore().getCurrentScore(), 180, 65);
    }
}
