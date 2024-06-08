package com.bubblebobble.views;

import com.bubblebobble.models.PowerUpModel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;

public class PowerUpView extends JComponent{

    private int x , y , width , height;
    PowerUpModel pwupModel;
    HashMap<String, PowerUpModel> pwupHash;

    public PowerUpView(PowerUpModel pwupModel , HashMap<String , PowerUpModel> pwupHash)
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

        for(Map.Entry<String, PowerUpModel> entry : pwupHash.entrySet())
        {
            String key = entry.getKey();
            PowerUpModel valModel = (PowerUpModel) entry.getValue();

            if(!valModel.isActive() == true)
            {
                if(key.equals("velocita"))
                {
                    // --- CAMBIARE QUESTO CODICE CON L'IMMAGINE DEL POWERUP ---

                    //g.setColor(Color.BLUE);
                    //g.fillRect(valModel.getX(), valModel.getY(), width, height);
                }

                if(key.equals("instakill"))
                {
                    //g.setColor(Color.green);
                    //g.fillRect(valModel.getX(), valModel.getY(), width, height);
                }

                if(key.equals("superjump"))
                {
                    //g.setColor(Color.red);
                    //g.fillRect(valModel.getX(), valModel.getY(), width, height);
                }

                if(key.equals("doppipunti"))
                {
                    //g.setColor(Color.pink);
                    //g.fillRect(valModel.getX(), valModel.getY(), width, height);
                }

                if (key.equals("killthemall"))
                {
                    //g.setColor(Color.gray);
                    //g.fillRect(valModel.getX(), valModel.getY(), width, height);
                }

                if(key.equals("freeze"))
                {
                    //g.setColor(Color.cyan);
                    //g.fillRect(valModel.getX(), valModel.getY(), width, height);
                }

                if(key.equals("freezeAndKill"))
                {
                    //g.setColor(Color.MAGENTA);
                    //g.fillRect(valModel.getX(), valModel.getY(), width, height);
                }

                if(key.equals("jumpPoints"))
                {
                    g.setColor(Color.BLUE);
                    g.fillRect(valModel.getX(), valModel.getY(), width, height);
                }

            }
        }
    
    }
}
