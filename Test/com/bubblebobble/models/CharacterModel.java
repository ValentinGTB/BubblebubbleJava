package com.bubblebobble.models;

public class CharacterModel {
    private int x;
	private int y;
    
    private int xSpeed = 0;
	private int ySpeed = 0;

    private double gravity = 1;

    public CharacterModel() {
    }

    public CharacterModel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
		this.x = x;
	}

	// Restituisce la posizione x del personaggio
	public int getX() {
		//System.out.println("X aggiornate --> " + x);
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	// Restituisce la posizione y del personaggio
	public int getY() {
		return y;
	}

    // Muove il personaggio
	public void move() {
		x += xSpeed;
		y += ySpeed;
		ySpeed += gravity;
	}

	// Imposta la velocità x del personaggio
	public void setXSpeed(int speed) {
		xSpeed = speed;
	}

	public int getXSpeed() {
		return xSpeed;
	}

	// Imposta la velocità y del personaggio
	public void setYSpeed(int speed) {
		ySpeed = speed;
	}

	public int getYSpeed() {
		return ySpeed;
	}
}
