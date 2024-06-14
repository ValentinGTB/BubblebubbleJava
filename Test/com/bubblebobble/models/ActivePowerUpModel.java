package com.bubblebobble.models;

import com.bubblebobble.contansts.PowerUpType;

/**
 * Questa classe rappresenta un power-up attivo.
 */
public class ActivePowerUpModel {
    private boolean active;
    private long activationTime;
    private PowerUpType powerUpType;
    private static final int DURATION = 5000; // Durata del power-up in millisecondi
    /**
     * Costruttore per creare un nuovo ActivePowerUpModel con il tipo specificato.
     *
     * @param powerUpType Il tipo di power-up.
     */
    public ActivePowerUpModel(PowerUpType powerUpType) {
        this.active = true;
        this.powerUpType = powerUpType;
        this.activationTime = System.currentTimeMillis(); // Prendi i secondi attuali all'attivazione del power-up
    }

    public void deactivate() {
        active = false;
    }
    /**
     * Verifica se il power-up è attivo.
     *
     * @return true se il power-up è attivo, false altrimenti.
     */
    public boolean isActive() {
        return active;
    }
    /**
     * Verifica se il power-up non è più attivo.
     *
     * @return true se il power-up è scaduto, false altrimenti.
     */
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
