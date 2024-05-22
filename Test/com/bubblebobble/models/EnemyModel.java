package com.bubblebobble.models;

import com.bubblebobble.Constants;

public class EnemyModel extends PlayerModel {

    PlayerModel pm;

    boolean colliding = false;
    boolean inAlto = false;
    int xEnemy = 70;
    int yEnemy = 680;
    int DISTANCEPG = 20;
    double enemySpeed = Constants.SPEED + 1.50;
    int xGiocatore;
    int yGiocatore;
    int flipFlop = 0;

    public EnemyModel(PlayerModel pm) {

        System.out.println("NEMICO CREATO!");
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

            /*
             * if(flipFlop == 1) ------ POSSIBILE LOGICA DA POTER USARE PER FAR COLPIRE
             * SUBITO UNA VOLTA SOLA IL GIOCATORE E POI PASSARE L'AZIONE AL THREAD
             * {
             * System.out.println("COLPISCI SUBITO!!");
             * flipFlop = 0;
             * }
             */

            Constants.colpito = true;
            // vanish del personaggio per 3 secondi player.vanish(3, 3) sta per 3 secondi e
            // il frame scompare e riappare per 3 secondi.
        }

    }

    public void distance(int deltaX, int deltaY) {
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY); // Formula per avvicinare il nemico al giocatore

        // Normalizza il vettore
        double directionX = deltaX / distance;

        // Aggiorna la posizione del nemico
        System.out.println(inAlto);
        if (colliding) {
            xEnemy += directionX * enemySpeed;

            if (inAlto) {
                //Aggiungi logica per far saltare il nemico lungo la piattaforma
                System.out.println("yEnemy dopo volo " + (yEnemy - 20) + " " + yGiocatore);
                // Controllo se mi trovo a una certa X di distanza verticalmente dal giocatore
                // (Evita di salire troppo vicino al giocatore)
            }
        }

        // TODO: Se c'è un ostacolo lo salto aggiornando anche le y

        // Se il nemico è sopra di me, dovrò saltare

    }

    int a = 0;

    @Override
    public boolean collidesWith(PlatformModel platform) {
        // Controlla se il personaggio è sopra la piattaforma e se la parte inferiore
        // del personaggio è al di sopra del punto superiore della piattaforma

        if (enemySpeed >= 0
                && yEnemy + DISTANCEPG >= platform.getPlatformY()
                && yEnemy <= platform.getPlatformY() + platform.getPlatformHeight()
                && xEnemy + DISTANCEPG >= platform.getPlatformX()
                && xEnemy <= platform.getPlatformX() + platform.getPlatformWidth()
                && yEnemy + DISTANCEPG <= platform.getPlatformY() + platform.getPlatformHeight()) {
            // La parte inferiore del personaggio è sopra il punto superiore della
            // piattaforma
            // La parte superiore del personaggio è al di sotto della piattaforma

            // Regola la posizione del personaggio per farlo rimanere sulla piattaforma
            yEnemy = platform.getPlatformY() - DISTANCEPG;

            // --- Se sono a terra, controllo se il giocatore sta più sopra di me ---
            // Il -20 serve a rimediare alla distanza di Y tra player e enemy
            if (yEnemy - 20 > yGiocatore) {
                // --- Controllo se sono alla fine della piattaforma ---
                // Sottraggo 15 di offset per far pensare all'enemy di essere a fine piattaforma
                // prima di arrivare al margine vero
                inAlto = true;
                a += 1;
                System.out.println("in alto aggiornata " + a + " " + inAlto);
                
                if (xEnemy == platform.getPlatformWidth() - 15) {

                }

                
            }
            // --- Altrimenti, se il giocatore è sulla mia stessa piattaforma ---
            else if (yEnemy - 20 == yGiocatore) {
                inAlto = false;
                System.out.println("yEnemy prima del volo " + (yEnemy - 20) + " " + yGiocatore);
            }

            return true; // Collisione rilevata
        }

        // Sezione di codice ragginuta SOLO SE il nemico non è più a terra

        return false; // Nessuna collisione rilevata
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

    public void setEnemyY(int yEnemy) {
        this.yEnemy = yEnemy;
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }

}