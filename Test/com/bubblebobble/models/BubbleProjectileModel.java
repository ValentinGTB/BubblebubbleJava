package com.bubblebobble.models;

import com.bubblebobble.contansts.Direction;

public class BubbleProjectileModel extends ProjectileModel {
    private String name;

    public BubbleProjectileModel(String name, int x, int y, int speed, Direction direction, CharacterModel thrower) {
        super(x, y, speed, direction, thrower);
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }
}
