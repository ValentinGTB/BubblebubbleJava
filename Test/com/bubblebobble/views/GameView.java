// File: com/bubblebobble/views/GameView.java
package com.bubblebobble.views;

import com.bubblebobble.Constants;
import com.bubblebobble.models.*;
import java.awt.*;
import javax.swing.*;
import java.util.List;

public class GameView extends JPanel {
    private PlayerView player;
    private List<PlatformView> platforms;
    private List<WallView> walls;
    private ProjectileModel projModel;
    private EnemyView enemyV;
    private ScoreModel scoreModel;

    public GameView(GameModel model, ScoreModel scoreModel) {
        player = new PlayerView(model.getPlayer());
        platforms = model.getPlatforms().stream().map(PlatformView::new).toList();
        walls = model.getWallModels().stream().map(WallView::new).toList();
        enemyV = new EnemyView(model.getEnemyModel());
        projModel = new ProjectileModel(300, 800, 0);
        this.scoreModel = scoreModel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!Constants.isGamePaused) {
            // disegna piattaforme
            platforms.forEach(platform -> platform.paintComponent(g));
            walls.forEach(wall -> wall.paintComponent(g));
            // disegna personaggio
            player.paintComponent(g);
            // Disegna nemico
            enemyV.paintComponent(g);
            // Disegna vita
            player.drawVita(g);
            // Disegna punteggio
            drawScore(g);
        } else {
            g.setColor(Color.white);
            g.fillRect(0, 0, Constants.MAX_WIDTH, Constants.MAX_HEIGHT);
        }
    }

    private void drawScore(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.RED);
        g.drawString("Score: " + scoreModel.getCurrentScore(), 750, 65);
    }
}
