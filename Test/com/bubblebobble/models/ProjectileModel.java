package com.bubblebobble.models;

public class ProjectileModel {
    private int x;
    private int y;
    private int speed;
    private boolean active;

    public ProjectileModel(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.active = true;
        System.out.println("pew pew");
    }

    public void move() {
        x += speed;
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
