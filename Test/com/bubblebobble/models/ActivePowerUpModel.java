package com.bubblebobble.models;

import com.bubblebobble.contansts.PowerUpType;

public class ActivePowerUpModel {
    private boolean active;
    private long activationTime;
    private PowerUpType powerUpType;
    private static final int DURATION = 5000; // Durata del power-up in millisecondi

    public ActivePowerUpModel(PowerUpType powerUpType) {
        this.active = true;
        this.powerUpType = powerUpType;
        this.activationTime = System.currentTimeMillis(); // Prendi i secondi attuali all'attivazione del power-up
    }

    public void deactivate() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isExpired() {
        /*
            Se isActive && il tempo attuale - il tempo preso all'attivazione > della durata massima del power-up, torna true.
        */
        return active && (System.currentTimeMillis() - activationTime > DURATION); 
    }

    public PowerUpType getPowerUpType() {
        return powerUpType;
    }

    public boolean is(PowerUpType type) {
        return getPowerUpType() == type;
    }
}
