package com.bubblebobble.models;

import com.bubblebobble.contansts.Direction;

public class ProjectileModel extends EntityModel {
    private int speed;
    private boolean active;
    private boolean visible;
    private Direction direction;

    public ProjectileModel(int x, int y, int speed, Direction direction) {
        super(x, y);
        this.speed = speed;
        this.active = true;
        this.direction = direction;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void move() {
        int delta = direction == Direction.LEFT ? -1 : 1;
        moveX(delta * speed);
    }

    public boolean collidesWith(EnemyModel enemy) {
        return getX() >= enemy.getX() && getX() <= enemy.getX() + 40 &&
               getY() >= enemy.getY() && getY() <= enemy.getY() + 40;
    }

    public boolean collidesWithWalls(int wallLeft, int wallRight) {
        return getX() <= wallLeft || getX() >= wallRight;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }
}
