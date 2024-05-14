package com.bubblebobble.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.bubblebobble.models.*;

public class PlayerView {
    // Array di immagini per l'animazione della camminata
    private int frameCount =0; // Conteggio dei frame
    private ImageIcon[] walkFrames;
    private int currentFrame = 0; // Frame corrente dell'animazione
    private int totalframe = 4;

    private PlayerModel model;

    public PlayerView(PlayerModel model) {
        this.model = model;

        // Carica le immagini dell'animazione della camminata
        walkFrames = new ImageIcon[totalframe]; // Supponiamo di avere 4 frame di animazione
        for (int i = 0; i < totalframe; i++) {
            walkFrames[i] = new ImageIcon(getClass().getResource("img/walk_" + i + ".png"));
        }
    }

    public ImageIcon getCurrentWalkFrame() {
        return walkFrames[currentFrame];
    }
    
    public void paintComponent(Graphics g) {
        // Disegna l'immagine corrente dell'animazione della camminata del personaggio
        g.drawImage(getCurrentWalkFrame().getImage(), model.getX(), model.getY(), null);
    }
}
