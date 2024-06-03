package com.bubblebobble.views;

import com.bubblebobble.models.PowerUpModel;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

public class PowerUpView extends JComponent{

    private int x , y , width , height;
    PowerUpModel pwupModel;

    public PowerUpView(PowerUpModel pwupModel)
    {
        x = pwupModel.getX();
        y = pwupModel.getY();
        width = pwupModel.getWidth();
        height = pwupModel.getHeight();
        this.pwupModel = pwupModel;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!pwupModel.isActive() == true)
        {g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);}
    }
}
