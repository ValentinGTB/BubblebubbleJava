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
        return x >= enemy.getEnemyX() && x <= enemy.getEnemyX() + 40 &&
               y >= enemy.getEnemyY() && y <= enemy.getEnemyY() + 40;
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