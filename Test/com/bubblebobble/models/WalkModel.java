package models;

import javax.swing.ImageIcon;

public class WalkModel {
     private int frameCount = 0; // Conteggio dei frame
    private ImageIcon[] walkFrames;
    private int currentFrame = 0; // Frame corrente dell'animazione
    private int totalframe = 6;

    public WalkModel() {
        // Carica le immagini dell'animazione della camminata
        walkFrames = new ImageIcon[totalframe]; // Supponiamo di avere 4 frame di animazione
        for (int i = 0; i < totalframe; i++) {
            walkFrames[i] = new ImageIcon(getClass().getResource("walk_flip_" + i + ".png"));
        }
    }

    // Restituisce l'immagine corrente per l'animazione della camminata
    public ImageIcon getCurrentWalkFrame() {
        return walkFrames[currentFrame];
    }

    public void Destra(){

    }

    public void Sinistra(){

    }
}
