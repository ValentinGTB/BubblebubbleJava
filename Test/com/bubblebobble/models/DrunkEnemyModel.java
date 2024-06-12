package com.bubblebobble.models;

import java.util.List;

import com.bubblebobble.Constants;

public class DrunkEnemyModel extends BaseEnemyModel {

    private final int DEFAULT_SPEED = 3;
    private long lastThrownBoomerangTime;

    public DrunkEnemyModel(int x, int y) {
        super(x, y);
        setSpeed(DEFAULT_SPEED);
        this.lastThrownBoomerangTime = System.currentTimeMillis();
    }

    private void freeze() {
        setSpeed(0);
        setXSpeed(0);
    }

    private void unfreeze() {
        setSpeed(DEFAULT_SPEED);
    }

    @Override
    protected void onUpdate() {
        super.onUpdate();

        long currentTime = System.currentTimeMillis();
        if (!isFruit() && !isInBubble()) {
            List<ProjectileModel> projectilesThrownByMe = GameModel.getInstance().getProjectiles().stream().filter(p -> p.getThrower() == this).toList();
            
            // impedisci di lasciare altri boomerang e rimani fermo finché abbiamo boomerang attivi
            if (!projectilesThrownByMe.isEmpty()) {
                freeze();
                return;
            } else {
                unfreeze();
            }
            
            // spara al giocatore
            if (currentTime - lastThrownBoomerangTime >= 3000) {
                ProjectileModel projectile = new BoomerangProjectileModel(getX() + getWidth(), getY() + getHeight() - 80 / 2,
                        Constants.PROJECTILE_SPEED, getDirection(), this);
                GameModel.getInstance().addProjectile(projectile);
                lastThrownBoomerangTime = System.currentTimeMillis();
            }
        }
    }
}
