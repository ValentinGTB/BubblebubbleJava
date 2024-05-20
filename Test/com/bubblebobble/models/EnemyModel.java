package com.bubblebobble.models;

import com.bubblebobble.Constants;

public class EnemyModel {

    int xEnemy = 70;
    int yEnemy = 600;

    PlayerModel pm;
    double enemySpeed = Constants.SPEED + 1.50;
    int xGiocatore;
    int yGiocatore;
    int flipFlop = 0; 

    public EnemyModel(PlayerModel pm) {
        System.out.print("CREATO");
        this.pm = pm;
        MyThread.getInstance().startThread(pm);
    }

    public void move() {

        if (xEnemy != xGiocatore || yEnemy != yGiocatore) {
            int deltaX = xGiocatore - xEnemy;
            int deltaY = yGiocatore - yEnemy;
            distance(deltaX, deltaY);
            Constants.colpito = false;
            flipFlop = 1;
        } else {
    
            /* if(flipFlop == 1)    ------ POSSIBILE LOGICA DA POTER USARE PER FAR COLPIRE SUBITO UNA VOLTA SOLA IL GIOCATORE E POI PASSARE L'AZIONE AL THREAD
            {
                System.out.println("COLPISCI SUBITO!!");
                flipFlop = 0;
            } */

            Constants.colpito = true;

            // vanish del personaggio per 3 secondi player.vanish(3, 3) sta per 3 secondi e
            // il frame scompare e riappare per 3 secondi.
        }

    }

    public void distance(int deltaX, int deltaY) {
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Normalizza il vettore
        double directionX = deltaX / distance;
        double directionY = deltaY / distance;

        // Aggiorna la posizione del nemico
        xEnemy += directionX * enemySpeed;
        yEnemy += directionY * enemySpeed;
    }

    public void setPlayerX(int x) {
        this.xGiocatore = x;
    }

    public void setPlayerY(int y) {
        this.yGiocatore = y;
    }

    public int getPlayerXEnemy() {
        return xGiocatore;
    }

    public int getPlayerYEnemy() {
        return yGiocatore;
    }

    public int getEnemyX() {
        return xEnemy;
    }

    public int getEnemyY() {
        return yEnemy;
    }

}
