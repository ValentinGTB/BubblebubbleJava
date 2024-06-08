package com.bubblebobble.models;

public class ProjectileModel {
    private int x;
    private int y;
    private int speed;
    private boolean active;
    private boolean visible;

    public ProjectileModel(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.active = true;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void move() {
        x += speed;
    }

    public boolean collidesWith(EnemyModel enemy) {
        return x >= enemy.getX() && x <= enemy.getX() + 40 &&
               y >= enemy.getY() && y <= enemy.getY() + 40;
    }

    public boolean collidesWithWalls(int wallLeft, int wallRight) {
        return x <= wallLeft || x >= wallRight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }
}
