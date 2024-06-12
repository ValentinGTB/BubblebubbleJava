package com.bubblebobble.models;

import com.bubblebobble.Constants;
import com.bubblebobble.contansts.PowerUpType;

public class WizardEnemeyModel extends EnemyModel {
    public WizardEnemeyModel(int x, int y) {
        super(x, y);
        setSpeed(3);
    }

    @Override
    protected void onUpdate() {
        // spara al giocatore
        if (System.currentTimeMillis() % 60 == 0) {
            ProjectileModel projectile = new RockProjectileModel(getX() + getWidth(), getY() + getHeight() - 80 / 2,
                    Constants.PROJECTILE_SPEED, getDirection(), this);
            GameModel.getInstance().addProjectile(projectile);
        }
    }
}
