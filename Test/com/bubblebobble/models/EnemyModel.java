package com.bubblebobble.models;

import com.bubblebobble.Constants;
import com.bubblebobble.controllers.GameController;

public class EnemyModel{
    
    int xEnemy = 50; 
    int yEnemy = 50;

    int enemySpeed = Constants.SPEED;

    int xGiocatore;
    int yGiocatore;
    GameController controller = new GameController(0);

    public EnemyModel() {
        System.out.print("CREATO");
    }

    public void move()
    {

        if(xEnemy != xGiocatore && yEnemy != yGiocatore)
        {
            int deltaX = xGiocatore - xEnemy;
            int deltaY = yGiocatore - yEnemy;
        }

        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Normalizza il vettore
        double directionX = deltaX / distance;
        double directionY = deltaY / distance;

        // Aggiorna la posizione del nemico
        xEnemy += directionX * enemySpeed;
        yEnemy += directionY * enemySpeed;
    }

    public void setPlayerX(int x)
    {
        this.xGiocatore = x;
    }

    public void setPlayerY(int y)
    {
        this.yGiocatore = y;
    }

    public int getPlayerXEnemy()
    {
        return xGiocatore;
    }

    public int getPlayerYEnemy()
    {
        return yGiocatore;
    }

    public int getEnemyX()
    {
        return xEnemy;
    }

    public int getEnemyY()
    {
        return yEnemy;
    }


}
