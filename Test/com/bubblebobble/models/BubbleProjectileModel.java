package com.bubblebobble.models;

import com.bubblebobble.contansts.Direction;

public class BubbleProjectileModel extends ProjectileModel {

    public BubbleProjectileModel(int x, int y, int speed, Direction direction, CharacterModel thrower) {
        super(x, y, speed, direction, thrower);
    }
    
    @Override
    public String getName() {
        return "Bubble";
    }
}
