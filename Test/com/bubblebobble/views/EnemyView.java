package com.bubblebobble.views;
import com.bubblebobble.Constants;
import com.bubblebobble.models.*;
import java.awt.*;
import javax.swing.JComponent;


public class EnemyView extends JComponent{
    
    EnemyModel enemy;
    int enemyX , enemyY;
    int playerX , playerY;
    private PlayerModel pm;
    private Image Enemy1 = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "Enemy1.png");

    public EnemyView(EnemyModel enemy) {

        this.enemy = enemy;
        if(enemy != null)
        {
            playerX = enemy.getPlayerXEnemy();
            playerY = enemy.getPlayerYEnemy();
            //System.out.print(playerX);
        }

    }

    @Override
    public void paintComponent(Graphics g) 
    {
        enemyX = enemy.getEnemyX();
        enemyY = enemy.getEnemyY();
        super.paintComponent(g);

        g.drawImage(Enemy1,enemyX, enemyY, null);

        /* DEBUG PER VEDERE SE PRENDE X E Y DEL GIOCATORE
         * g.setColor(Color.green);
         * g.fillRect(enemy.getPlayerXEnemy(), enemy.getPlayerYEnemy(), 10, 10);
        */
        
    
    }

}
