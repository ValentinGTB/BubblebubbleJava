package com.bubblebobble.views;

import com.bubblebobble.models.EnemyModel;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

public class EnemyView extends JComponent{
    
    EnemyModel enemy;
    int playerX , playerY;


    public EnemyView(EnemyModel enemy) {

        this.enemy = enemy;

        playerX = enemy.getPlayerXEnemy();
        playerY = enemy.getPlayerYEnemy();
    }

    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        g.setColor(Color.green);
        g.fillRect(enemy.getPlayerXEnemy(), enemy.getPlayerYEnemy(), 100, 100);

    }

}
