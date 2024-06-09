package com.bubblebobble.models;

import com.bubblebobble.contansts.Direction;

public class ProjectileModel extends EntityModel {
    private int speed;
    private boolean active;
    private boolean visible;
    private Direction direction;
    private long activationTime;

    public ProjectileModel(int x, int y, int speed, Direction direction) {
        super(x, y, 40, 40);
        this.speed = speed;
        this.active = true;
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
}
