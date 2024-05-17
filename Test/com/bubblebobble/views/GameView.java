package com.bubblebobble.views;

import com.bubblebobble.Constants;
import com.bubblebobble.models.*;
import java.awt.*;
import javax.swing.*;

public class GameView extends JPanel {

    private GameModel model;
    private PlayerView player;
    private java.util.List<PlatformView> platforms;
    private EnemyView enemyV;


    public GameView(GameModel model) {
        player = new PlayerView(model.getPlayer());
        platforms = model.getPlatforms().stream().map(PlatformView::new).toList();
        enemyV = new EnemyView(model.getEnemyModel());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(Constants.isGamePaused == false) {
        	// disegna piattaforme
        	platforms.forEach(platform -> platform.paintComponent(g));
        	// disegna personaggio
        	player.paintComponent(g);
            //Disegna nemico
            enemyV.paintComponent(g);
        }
        else 
        {
        	g.setColor(Color.white);
            g.fillRect(0, 0, Constants.MAX_WIDTH, Constants.MAX_HEIGHT);
        }
        
    }
}
