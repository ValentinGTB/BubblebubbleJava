package com.bubblebobble.models;

import com.bubblebobble.contansts.Direction;

public class FireBallProjectileModel extends ProjectileModel {

    public FireBallProjectileModel(int x, int y, int speed, Direction direction, CharacterModel thrower) {
        super(x, y, speed, direction, thrower);
    }
    
    @Override
    public String getName() {
        return "Fireball";
    }
}
