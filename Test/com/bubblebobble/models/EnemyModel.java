package com.bubblebobble.models;

import com.bubblebobble.controllers.GameController;

public class EnemyModel {
    
    int xGiocatore;
    int yGiocatore;
    GameController controller = new GameController(0);

    public EnemyModel() {
        System.out.print("CREATO");
    }
    
    

    public void setPlayerX(int x)
    {
        this.xGiocatore = x;
        System.out.println("X --> " + xGiocatore);
    }

    public void setPlayerY(int y)
    {
        this.yGiocatore = y;
        System.out.println(" " + "Y --> " + yGiocatore);
    }

    public int getPlayerXEnemy()
    {
        return xGiocatore;
    }

    public int getPlayerYEnemy()
    {
        return yGiocatore;
    }


}
