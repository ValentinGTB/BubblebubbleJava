package com.bubblebobble.models;

import com.bubblebobble.Constants;

public class EnemyModel {

    int xEnemy = 50;
    int yEnemy = 50;

    PlayerModel pm;
    double enemySpeed = Constants.SPEED + 2.50;
    int xGiocatore;
    int yGiocatore;

    public EnemyModel(PlayerModel pm) {
        System.out.print("CREATO");
        this.pm = pm;
    }

    public void move() {

        if (xEnemy != xGiocatore || yEnemy != yGiocatore) {
            int deltaX = xGiocatore - xEnemy;
            int deltaY = yGiocatore - yEnemy;
            distance(deltaX, deltaY);

        } else {

            new Thread(new Runnable() {
                public void run() {
                    try {

                        // Se tocchi il player -> player.vita(-1)
                        
                        if (pm.getVita() > 0) {
                            pm.setVita();
                        }
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println("interrotto male");
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }

                }
            }).start();

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
