package com.bubblebobble.views;

import com.bubblebobble.Constants;
import com.bubblebobble.models.*;
import java.awt.*;
import javax.swing.*;

public class PlayerView {
    // Array di immagini per l'animazione della camminata
    private int frameCount = 0; // Conteggio dei frame
    private ImageIcon[] walkFrames;
    private int currentFrame = 0; // Frame corrente dell'animazione
    private int totalframe = 4;
    private int vitaAttuale = 3;

    private Image vita = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "vita.png");
    private Image gameOver = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "gameOver.png");

    private PlayerModel model;

    public PlayerView() {
    } // Costruttore vuoto

    public PlayerView(PlayerModel model) {
        this.model = model;

        // Carica le immagini dell'animazione della camminata
        walkFrames = new ImageIcon[totalframe]; // Supponiamo di avere 4 frame di animazione
        for (int i = 0; i < totalframe; i++) {
            walkFrames[i] = new ImageIcon(getClass().getResource("img/walk_" + i + ".png"));
        }
    }

    public void setVitaView(int vita) {
        vitaAttuale = vita;
        System.out.println("VITA ATTUALE" + vitaAttuale);
    } // Imposta valore della vita

    public ImageIcon getCurrentWalkFrame() {
        return walkFrames[currentFrame];
    }

    public void paintComponent(Graphics g) {
        // Disegna l'immagine corrente dell'animazione della camminata del personaggio
        g.drawImage(getCurrentWalkFrame().getImage(), model.getX(), model.getY(), null);

        // Disegna vita

        int xMassima = 90;
        // Stampa j volte l'immagine fino ad arrivare alla vitaAttuale
        // La vitaAttuale viene decrementata per cui il for arriverà sempre a meno
        for (int j = 1; j <= vitaAttuale; j++) {
            System.out.println(vitaAttuale);
            g.drawImage(vita, xMassima, 50, null);
            xMassima -= 20; // Stampa i cuori a 20 di distanza da destra a sinistra togliendo 20 dalla dist
                            // massima ad ogni ciclata
        }
        if (vitaAttuale <= 0) {
            g.drawImage(gameOver, 50, 50, null);
        }

    }
}
