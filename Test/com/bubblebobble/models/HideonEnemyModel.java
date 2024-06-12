package com.bubblebobble.models;

import com.bubblebobble.Constants;

public class HideonEnemyModel extends BaseEnemyModel {

    public HideonEnemyModel(int x, int y) {
        super(x, y);
    }
    
    @Override
    protected void onUpdate() {
        super.onUpdate();
        
        // spara al giocatore
        if (System.currentTimeMillis() % 60 == 0) {
            ProjectileModel projectile = new FireBallProjectileModel(getX() + getWidth(), getY() + getHeight() - 80 / 2,
                    Constants.PROJECTILE_SPEED * 2, getDirection(), this);

            GameModel.getInstance().addProjectile(projectile);
        }
    }

}
