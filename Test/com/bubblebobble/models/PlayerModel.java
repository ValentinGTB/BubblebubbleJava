package com.bubblebobble.models;

import com.bubblebobble.Constants;
import com.bubblebobble.contansts.Direction;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerModel extends CharacterModel {
	int lives = Constants.VITA;

	public PlayerModel() {
		super();
	}

	public PlayerModel(int x, int y) {
		super(x, y);
	}

	public void shoot() {
		ProjectileModel projectile = new ProjectileModel(getX() + getWidth(), getY() + getHeight() - 80 / 2,
				Constants.PROJECTILE_SPEED, getDirection());
		GameModel.getInstance().addProjectile(projectile);
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

	@Override
	public void setJumping(boolean jumping) {
		super.setJumping(jumping);

		if (jumping) {
			for (Map.Entry<String, PowerUpModel> entry : GameModel.getInstance().getPowerUps().entrySet()) {
				String key = entry.getKey();
				PowerUpModel valModel = (PowerUpModel) entry.getValue();

				if (key.equals("superjump")) {

					if (valModel.isActive() && !valModel.isExpired()) {
						setYSpeed(-20);
					} else {
						setYSpeed(-15);
					}
				}
			}
		}
	}
}