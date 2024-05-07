package com.bubblebobble.models;

public class PlayerModel {

    private int x;
    private int y;

    private int xSpeed = 0;
    private int ySpeed = 0;

    private int DISTANCEPG = 40;
    private double gravity = 1;
    
    private boolean isJumping;

    public PlayerModel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    // Restituisce la posizione x del personaggio
    public int getX() {
        return x;
    }   

    public void setY(int y) {
        this.y = y;
    }

    // Restituisce la posizione y del personaggio
    public int getY() {
        return y;
    }

    public void salta() {
        if (!isJumping)
            setJumping(true);
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        if (jumping) {
            ySpeed = -15; 
            isJumping = true; 
        } else {
            ySpeed = 0;
            isJumping = false;
        }
    }

    public boolean collidesWith(PlatformModel platform) {
        // da controllare meglio.
        return
            y + DISTANCEPG >= platform.getPlatformY() && y <= platform.getPlatformY() + platform.getPlatformHeight() &&
            x + DISTANCEPG >= platform.getPlatformX() && x <= platform.getPlatformX() + platform.getPlatformWidth();
    }

    // Muove il personaggio
    public void move() {
        x += xSpeed;
        y += ySpeed;
        ySpeed += gravity;

        // private int platformX = 0;
        // private int platformY = 230;
        // private int platformWidth = 400;
        // private int platformHeight = 20;

        // Tracciamento tocco della piattaforma


        // if (xSpeed != 0) {
        //     frameCount++;
            
        //     // Aggiorna l'animazione ogni 5 frame (puoi regolare questo valore per rallentare o accelerare l'animazione)
        //     if (frameCount % 5 == 0) {
        //         currentFrame = (currentFrame + 1) % 4;
        //     }
        // } else {
        //     frameCount = 0;
        // }
    }

    // Imposta la velocità x del personaggio
    public void setXSpeed(int speed) {
        xSpeed = speed;
    }

    public int getXSpeed() {
        return xSpeed;
    }

    // Imposta la velocità y del personaggio
    public void setYSpeed(int speed) {
        ySpeed = speed;
    }

    public int getYSpeed() {
        return ySpeed;
    }
}