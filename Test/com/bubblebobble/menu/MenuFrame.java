package com.bubblebobble.menu;

import java.awt.Color;
import java.awt.Window;

import javax.swing.JFrame;

import com.bubblebobble.Constants;

public class MenuFrame extends JFrame{
	
		Menu pannello = new Menu();
	
		public MenuFrame() 
		{
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setResizable(false);
			setTitle("menu");
			add(pannello);
			
			setSize(Constants.MAX_WIDTH , Constants.MAX_HEIGHT);

			setLocationRelativeTo(null);
			setVisible(true);
			
		}
	
}
