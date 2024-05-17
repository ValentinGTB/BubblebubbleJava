package com.bubblebobble.views;
import com.bubblebobble.models.*;
import java.awt.Graphics;
import javax.swing.JComponent;

public class EnemyView extends JComponent{
    
    EnemyModel enemy;
    int playerX , playerY;
    private PlayerModel pm;

    public EnemyView(EnemyModel enemy) {

        this.enemy = enemy;
        if(enemy != null)
        {
            playerX = enemy.getPlayerXEnemy();
            playerY = enemy.getPlayerYEnemy();
            System.out.print(playerX);
        }

    }

    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        

        /* DEBUG PER VEDERE SE PRENDE X E Y DEL GIOCATORE
         * g.setColor(Color.green);
         * g.fillRect(enemy.getPlayerXEnemy(), enemy.getPlayerYEnemy(), 10, 10);
        */
        
    
    }

}
