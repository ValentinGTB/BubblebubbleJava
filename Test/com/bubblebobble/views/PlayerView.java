package com.bubblebobble.views;

import com.bubblebobble.Constants;
import com.bubblebobble.models.*;
import java.awt.*;
import javax.swing.*;

public class PlayerView {
    // Array di immagini per l'animazione della camminata
    private int frameCount =0; // Conteggio dei frame
    private ImageIcon[] walkFrames;
    private int currentFrame = 0; // Frame corrente dell'animazione
    private int totalframe = 4;

    private Image vita = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "vita.png");
    private Image gameOver = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "gameOver.png");

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
        //Disegna vita
        if(model.getVita() == 3){
            
            g.drawImage(vita, 50, 50, null);
            g.drawImage(vita, 70, 50, null);
            g.drawImage(vita, 90, 50, null);
        }
        else if(model.getVita() == 2)
        {
            g.drawImage(vita, 50, 50, null);
            g.drawImage(vita, 70, 50, null);
           
        }
        else if(model.getVita() == 1)
        {
            g.drawImage(vita, 50, 50, null);
        }
        else
        {
            g.drawImage(gameOver, 50, 50, null);
        }
    }
}
