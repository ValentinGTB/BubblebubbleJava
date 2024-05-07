package com.bubblebobble.models;

public class PlatformModel {
    private int platformWidth;
    private int platformHeight;

    private int platformX;
    private int platformY;

    public PlatformModel(int x, int y, int maxWidth, int maxHeight) {
        this.setDimension(maxWidth, maxHeight);
        this.setPosition(x, y);
    }

    public void setPosition(int x, int y) {
        this.platformX = x;
        this.platformY = y;
    }

    public void setDimension(int width, int height) {
        this.platformWidth = width;
        this.platformHeight = height;
    }

    public int getPlatformX() {
        return this.platformX;
    }

    public int getPlatformY() {
        return this.platformY;
    }

    public int getPlatformWidth() {
        return this.platformWidth;
    }

    public int getPlatformHeight() {
        return this.platformHeight;
    }
}
