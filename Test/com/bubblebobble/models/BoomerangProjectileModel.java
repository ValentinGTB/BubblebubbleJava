package com.bubblebobble.models;

import com.bubblebobble.contansts.Direction;

public class BoomerangProjectileModel extends ProjectileModel {

    int initialX;
    int initialY;
    int distance;
    int distanceLeft;

    boolean returning;

    public BoomerangProjectileModel(int x, int y, int speed, Direction direction, CharacterModel thrower) {
        super(x, y, speed, direction, thrower);
        this.initialX = x;
        this.initialY = y;
        this.returning = false;
        this.distance = distanceLeft = 360;
    }

    @Override
    public void move() {
        if (!isActive())
            return;
        
        int delta = getDirection() == Direction.LEFT ? -1 : 1;
        int mov = delta * getSpeed();

        if (distanceLeft > 0) {
            distanceLeft -= Math.max(0, Math.abs(mov));
        } else if (!returning) {
            setDirection(getDirection() == Direction.LEFT ? Direction.RIGHT : Direction.LEFT);
            returning = true;
        }

        if (returning && Math.abs(getX() - initialX) <= 10) {
            deactivate();
            return;
        }

        moveX(mov);
    }
    
    @Override
    public String getName() {
        return "Boomerang";
    }
}
