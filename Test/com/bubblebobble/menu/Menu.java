package com.bubblebobble.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import com.bubblebobble.Constants;
import com.bubblebobble.Main;

public class Menu extends JPanel{
	
	    Image image = Toolkit.getDefaultToolkit().getImage("C:/Users/tbone/OneDrive/Documenti/UNIVERSITA/BubblebubbleJava/BubblebubbleJava/Test/com/bubblebobble/views/img/start.png");
	    JButton button = new JButton();
	    
	   
	    
	    public Menu() 
		{
	    		setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
				button.setBackground(Color.green);
				button.setText("premimi");
				button.setPreferredSize(new Dimension(500,200)); //Imposta grandezza con PreferredSize visto che si sta usando il FlowLayout manager
				
				add(Box.createVerticalStrut(200)); //Aggiungi uno spessore invisibile tra il bordo del pannello e il pulsante
				add(Box.createHorizontalStrut(150));
				button.addMouseListener(new MouseAdapter() 
				{
					@Override
					public void mouseClicked(MouseEvent e) 
					{
						SwingUtilities.invokeLater(Main::new);
						Constants.MENUAPERTO = false;
					}
				});
				
				
				setBackground(Color.black);
				add(button);
				add(new JButton("ciao"));
				add(new JButton("prova"));
				add(new JButton("mondo"));
				setVisible(true);
				requestFocus(); //Richiedi che questo pannello sia pronto a ricevere input da mouse o tastiera appena visualizzato sopra a tutti gli altri
				
				repaint();
				
		}

		public void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			g.drawImage(image , Constants.MAX_WIDTH /2 , 0 , this );
			
		}
		
}
