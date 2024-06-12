package com.bubblebobble.models;

import com.bubblebobble.contansts.PowerUpType;

public class PowerUpModel extends EntityModel {
    private PowerUpType powerUpType;

    public PowerUpModel(PowerUpType powerUpType, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.powerUpType = powerUpType;
    }
    
    public PowerUpType getPowerUpType() {
        return powerUpType;
    }

    public boolean is(PowerUpType type) {
        return getPowerUpType() == type;
    }
}
