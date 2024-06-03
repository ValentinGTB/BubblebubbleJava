package com.bubblebobble.models;

public class PowerUpModel {
    private int x, y, width, height;
    private boolean isActive;
    private long activationTime;
    private static final int DURATION = 5000; // Durata del power-up in millisecondi

    public PowerUpModel(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isActive = false;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
