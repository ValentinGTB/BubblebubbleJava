package com.bubblebobble.models;

public class CharacterModel extends EntityModel {
    private int xSpeed = 0;
	private int ySpeed = 0;

    private double gravity = 1;

    public CharacterModel() {
    }

    public CharacterModel(int x, int y) {
        super(x, y);
    }

    // Muove il personaggio
	public void move() {
        moveX(xSpeed);
        moveY(ySpeed);
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
