package com.bubblebobble.models;

import com.bubblebobble.contansts.Direction;
/**
 * Questa classe rappresenta il modello di base per un personaggio nel gioco.
 * Estende EntityModel e gestisce la posizione, la velocità e lo stato del personaggio.
 * Fornisce metodi per muovere il personaggio, farlo saltare e impostare la direzione di movimento.
 * Viene usata anche in EnemyModel.
 */
public class CharacterModel extends EntityModel {
    private int xSpeed = 0;
	private int ySpeed = 0;
	private boolean isJumping = false;
	private Direction direction = Direction.LEFT;

    public CharacterModel() {
    }
/**
     * Costruttore per creare un personaggio con coordinate iniziali e dimensioni specificate.
     *
     * @param x coordinata x iniziale
     * @param y coordinata y iniziale
     * @param width larghezza del personaggio
     * @param height altezza del personaggio
     */
    public CharacterModel(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    /**
     * Metodo per muovere il personaggio in base alla sua velocità attuale.
     * Sposta il personaggio sia sull'asse x che sull'asse y.
     */
    // Muove il personaggio
	public void move() {
        moveX(xSpeed);
        moveY(ySpeed);
	}
    /**
     * Metodo per far saltare il personaggio di una certa altezza.
     * Il personaggio può saltare solo se non è già in aria.
     *
     * @param height altezza del salto
     */
	public void jump(int height) {
		if (!isJumping()) {
			setJumping(true);
			setYSpeed(-height);
		}
	}
    /**
     * Metodo per impostare lo stato del salto del personaggio.
     *
     * @param jumping true se il personaggio sta saltando, false altrimenti
     */
	public void setJumping(boolean jumping) {
		if (jumping) {
			setYSpeed(-15);
			isJumping = true;
		} else {
			setYSpeed(0);
			isJumping = false;
		}
	}
    /**
     * Metodo per verificare se il personaggio sta saltando.
     *
     * @return true se il personaggio sta saltando, false altrimenti
     */
	public boolean isJumping() {
		return isJumping;
	}

	// Imposta la velocità x del personaggio
	public void setXSpeed(int speed) {
		xSpeed = speed;
		
		if (xSpeed != 0)
			direction = xSpeed < 0 ? Direction.LEFT : Direction.RIGHT;
	}

	public int getXSpeed() {
		return xSpeed;
	}

	// Imposta la velocità y del personaggio
	public void setYSpeed(int speed) {
		if (ySpeed != speed){
			ySpeed = speed;
		}
	}

	public int getYSpeed() {
		return ySpeed;
	}
    /**
     * Metodo per ottenere la direzione attuale del movimento del personaggio.
     *
     * @return la direzione del movimento del personaggio (sinistra o destra)
     */
	public Direction getDirection() {
		return direction;
	}
}
