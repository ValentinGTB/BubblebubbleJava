package com.bubblebobble.models;

import java.awt.Image;

public class PowerUpModel extends EntityModel {
    private Image immagine;
    private int width, height;
    private boolean isActive;
    private long activationTime;
    private static final int DURATION = 5000; // Durata del power-up in millisecondi

    public PowerUpModel(int x, int y, int width, int height , Image immagine) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.isActive = false;
        this.immagine = immagine;
    }

    public void activate() {
        isActive = true;
        activationTime = System.currentTimeMillis(); // Prendi i secondi attuali all'attivazione del power-up
    }

    public void deactivate() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isExpired() {
        return isActive && (System.currentTimeMillis() - activationTime > DURATION); 
        /*
            Se isActive && il tempo attuale - il tempo preso all'attivazione > della durata massima del power-up, torna true.
        */                                                                                    
    }

    // --- GETTER E SETTER --- 

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImmagine(){return immagine;}
}
