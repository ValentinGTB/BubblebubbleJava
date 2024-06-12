package com.bubblebobble.models;

import com.bubblebobble.Constants;
import com.bubblebobble.contansts.PowerUpType;

import java.awt.event.ActionEvent;

public class PlayerModel extends CharacterModel {
	private int lives = Constants.PLAYER_LIVES;

	public PlayerModel() {
		super();
	}

	public PlayerModel(int x, int y) {
		super(x, y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
	}

	public void shoot() {
		String bubbleType = GameModel.getInstance().hasPowerup(PowerUpType.Instakill) ? "RedBubble" : "Bubble";
		ProjectileModel projectile = new BubbleProjectileModel(bubbleType, getX() + getWidth(), getY() + getHeight() - 80 / 2,
				Constants.PROJECTILE_SPEED, getDirection(), this);

		GameModel.getInstance().addProjectile(projectile);
	}

	public void increaseLife() {
		lives += 1;
	}

	public void decreaseLife() {
		if (lives > 0) {
			lives -= 1;
			if (lives <= 0) {
				GameModel.getInstance().notify("gameOver", new ActionEvent(this, 0, "setVita"));
			}
		}
	}

	public int getLives() {
		return lives;
	}
}