package com.bubblebobble.models;

import com.bubblebobble.contansts.Direction;

public class RockProjectileModel extends ProjectileModel {

    public RockProjectileModel(int x, int y, int speed, Direction direction, CharacterModel thrower) {
        super(x, y, speed, direction, thrower);
    }
    
    @Override
    public String getName() {
        return "Rock";
    }
}
