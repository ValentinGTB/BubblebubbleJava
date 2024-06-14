package com.bubblebobble.models;

import com.bubblebobble.contansts.Direction;
/**
 * Classe astratta che rappresenta un proiettile.
 * Estende la classe EntityModel.
 */
public abstract class ProjectileModel extends EntityModel {
    private int speed;
    private boolean active;
    private Direction direction;
    private long activationTime;
    private CharacterModel thrower;
    /**
     * Costruttore della classe ProjectileModel.
     * 
     * @param x         la coordinata x iniziale del proiettile
     * @param y         la coordinata y iniziale del proiettile
     * @param speed     la velocità del proiettile
     * @param direction la direzione del proiettile
     * @param thrower   il personaggio che ha lanciato il proiettile
     */
    public ProjectileModel(int x, int y, int speed, Direction direction, CharacterModel thrower) {
        super(x, y, 40, 40);
        this.speed = speed;
        this.active = true;
        this.thrower = thrower;
        this.direction = direction;
        this.activationTime = System.currentTimeMillis();
    }
    /**
     * Muove il proiettile nella direzione specificata con la velocità specificata.
     */
    public void move() {
        int delta = direction == Direction.LEFT ? -1 : 1;
        moveX(delta * speed);
    }
    /**
     * Verifica se il proiettile è attivo.
     * 
     * @return true se il proiettile è attivo, false altrimenti
     */
    public boolean isActive() {
        return active;
    }
    /**
     * Disattiva il proiettile.
     */
    public void deactivate() {
        active = false;
    }
    /**
     * Restituisce il tempo di attivazione del proiettile.
     * 
     * @return il tempo di attivazione del proiettile in millisecondi
     */
    public long getActivationTime() {
        return activationTime;
    }
    /**
     * Restituisce il personaggio che ha lanciato il proiettile.
     * 
     * @return il personaggio che ha lanciato il proiettile
     */
    public CharacterModel getThrower() {
        return thrower;
    }

    public abstract String getName();

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }
}
