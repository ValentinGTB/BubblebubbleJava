package com.bubblebobble.models;

import com.bubblebobble.contansts.Direction;

public class CharacterModel extends EntityModel {
    private int xSpeed = 0;
	private int ySpeed = 0;
	private double gravity = 1;
	private Direction direction = Direction.LEFT;

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
		
		if (xSpeed != 0)
			direction = xSpeed < 0 ? Direction.LEFT : Direction.RIGHT;
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

	public Direction getDirection() {
		return direction;
	}
}
