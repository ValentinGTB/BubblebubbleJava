package com.bubblebobble.models;

import com.bubblebobble.contansts.Direction;

public class CharacterModel extends EntityModel {
    private int xSpeed = 0;
	private int ySpeed = 0;
	private boolean isJumping = false;
	private Direction direction = Direction.LEFT;

    public CharacterModel() {
    }

    public CharacterModel(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    // Muove il personaggio
	public void move() {
        moveX(xSpeed);
        moveY(ySpeed);
	}

	public void jump(int height) {
		if (!isJumping()) {
			setJumping(true);
			setYSpeed(-height);
		}
	}

	public void setJumping(boolean jumping) {
		if (jumping) {
			setYSpeed(-15);
			isJumping = true;
		} else {
			setYSpeed(0);
			isJumping = false;
		}
	}

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

	public Direction getDirection() {
		return direction;
	}
}
