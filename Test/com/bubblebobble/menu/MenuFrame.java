package com.bubblebobble.menu;

import com.bubblebobble.Constants;
import javax.swing.JFrame;

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
