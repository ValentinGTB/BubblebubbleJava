// File: com/bubblebobble/views/GameView.java
package com.bubblebobble.views;

import com.bubblebobble.Constants;
import com.bubblebobble.models.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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
        // Carica il font personalizzato da un file TTF
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File(Constants.BaseURL + "arcade-legacy.ttf"));
            customFont = customFont.deriveFont(Font.BOLD, 16); // Imposta la dimensione del font
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            // Gestisci l'eccezione in modo appropriato
        }
        
        if (customFont != null) {
            // Utilizza il font personalizzato per disegnare il punteggio
            g.setFont(customFont);
            g.setColor(Color.WHITE);
            g.drawString("Score: " + scoreModel.getCurrentScore(), 180, 65);
        } else {
            // Se il caricamento del font fallisce, utilizza un fallback font
            g.setFont(new Font("Courier New", Font.BOLD, 32));
            g.setColor(Color.RED);
            g.drawString("Score: " + scoreModel.getCurrentScore(), 750, 65);
        }
    }
}
