package com.bubblebobble.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

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
        
        // disegna piattaforme
        platforms.forEach(platform -> platform.paintComponent(g));

        // disegna personaggio
        player.paintComponent(g);
    }
}
