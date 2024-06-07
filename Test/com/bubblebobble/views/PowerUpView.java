package com.bubblebobble.views;

import com.bubblebobble.models.PowerUpModel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;

public class PowerUpView extends JComponent{

    private int x , y , width , height;
    PowerUpModel pwupModel;
    HashMap<String, ArrayList<Object>> pwupHash;

    public PowerUpView(PowerUpModel pwupModel , HashMap<String , ArrayList<Object>> pwupHash)
    {

        x = pwupModel.getX();
        y = pwupModel.getY();
        width = pwupModel.getWidth();
        height = pwupModel.getHeight();
        this.pwupHash = pwupHash;
        this.pwupModel = pwupModel;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(Map.Entry<String, ArrayList<Object>> entry : pwupHash.entrySet())
        {
            String key = entry.getKey();
            PowerUpModel valModel = (PowerUpModel) entry.getValue().get(0);

            if(!valModel.isActive() == true)
            {
                if(key.equals("velocita"))
                {
                    // --- CAMBIARE QUESTO CODICE CON L'IMMAGINE DEL POWERUP ---

                    g.setColor(Color.BLUE);
                    g.fillRect(valModel.getX(), valModel.getY(), width, height);
                }

                if(key.equals("instakill"))
                {
                    g.setColor(Color.green);
                    g.fillRect(valModel.getX(), valModel.getY(), width, height);
                }

                if(key.equals("superjump"))
                {
                    g.setColor(Color.red);
                    g.fillRect(valModel.getX(), valModel.getY(), width, height);
                }

                if(key.equals("doppipunti"))
                {
                    g.setColor(Color.pink);
                    g.fillRect(valModel.getX(), valModel.getY(), width, height);
                }

            }
        }
    
    }
}
