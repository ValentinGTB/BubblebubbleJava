package com.bubblebobble.models;

public class WallModel {
    private int wallWidth;
    private int wallHeight;

    private int wallX;
    private int wallY;

    public WallModel(int x, int y, int maxWidth, int maxHeight) {
        this.setDimension(maxWidth, maxHeight);
        this.setPosition(x, y);
    }

    public void setPosition(int x, int y) {
        this.wallX = x;
        this.wallY = y;
    }

    public void setDimension(int width, int height) {
        this.wallWidth = width;
        this.wallHeight = height;
    }

    public int getWallX() {
        return this.wallX;
    }

    public int getWallY() {
        return this.wallY;
    }

    public int getWallWidth() {
        return this.wallWidth;
    }

    public int getWallHeight() {
        return this.wallHeight;
    }
}
