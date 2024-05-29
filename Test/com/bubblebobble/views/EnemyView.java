package com.bubblebobble.views;

import com.bubblebobble.Constants;
import com.bubblebobble.models.*;
import java.awt.*;
import javax.swing.JComponent;


public class EnemyView extends JComponent{
    
    EnemyModel enemy;
    int enemyX , enemyY;
    int playerX , playerY;
    private Image enemyImage;
    private Image bubbleImage;
    private PlayerModel pm;
    private Image Enemy1 = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "Enemy1.png");

    public EnemyView(EnemyModel enemy) {

        this.enemyImage = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "Enemy1.png");
        this.bubbleImage = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "pallanemica.png");
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

        if (enemy.isInBubble()) {
            g.drawImage(bubbleImage, enemyX, enemyY, Constants.ALL_PLATFORMHEIGHT, Constants.ALL_PLATFORMHEIGHT, this);
        } else {
            g.drawImage(enemyImage, enemyX, enemyY, Constants.ALL_PLATFORMHEIGHT, Constants.ALL_PLATFORMHEIGHT, this);
        }

    }

}
