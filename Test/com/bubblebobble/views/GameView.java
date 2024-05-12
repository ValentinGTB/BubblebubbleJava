package com.bubblebobble.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.bubblebobble.Constants;
import com.bubblebobble.controllers.GameController;
import com.bubblebobble.models.*;

public class GameView extends JPanel {
    private PlayerView player;
    private java.util.List<PlatformView> platforms;
    

    public GameView(GameModel model) {
    	
        player = new PlayerView(model.getPlayer());
        platforms = model.getPlatforms().stream().map(PlatformView::new).toList();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(Constants.isGamePaused == false) {
        	// disegna piattaforme
        	platforms.forEach(platform -> platform.paintComponent(g));
        	// disegna personaggio
        	player.paintComponent(g);
        }
        else 
        {
        	g.setColor(Color.white);
            g.fillRect(0, 0, Constants.MAX_WIDTH, Constants.MAX_HEIGHT);
        }
        
    }
}
