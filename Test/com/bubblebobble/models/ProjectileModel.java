package com.bubblebobble.models;

import com.bubblebobble.contansts.Direction;

public abstract class ProjectileModel extends EntityModel {
    private int speed;
    private boolean active;
    private boolean visible;
    private Direction direction;
    private long activationTime;
    private CharacterModel thrower;

    public ProjectileModel(int x, int y, int speed, Direction direction, CharacterModel thrower) {
        super(x, y, 40, 40);
        this.speed = speed;
        this.active = true;
        this.thrower = thrower;
        this.direction = direction;
        this.activationTime = System.currentTimeMillis();
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void move() {
        int delta = direction == Direction.LEFT ? -1 : 1;
        moveX(delta * speed);
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    public long getActivationTime() {
        return activationTime;
    }

    public CharacterModel getThrower() {
        return thrower;
    }

    public abstract String getName();

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }
}
