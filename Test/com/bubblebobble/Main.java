package com.bubblebobble;
import javax.swing.*;

import com.bubblebobble.controllers.*;
import com.bubblebobble.menu.MenuFrame;
import com.bubblebobble.views.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;

public class Main extends JFrame {
    
	private GameController controller;
	static MenuFrame menu;

	Timer timer;
	private JPanel inGamePanel;
	private JButton closeButton;
	boolean isPaused = false;
    private JButton esciButton;


    public Main() {	
    	
    
    	
    	inGamePanel = new JPanel();
    	inGamePanel.setBackground(Color.red);
    	//inGamePanel.setPreferredSize(new Dimension(300,300));
    	//inGamePanel.setLocation(300,300);
    	inGamePanel.setVisible(false);
    	
    	closeButton = new JButton("Continua");
        closeButton.addActionListener(new ActionListener() {
        
			@Override
            public void actionPerformed(ActionEvent e) {

				if (!isPaused) {
                    timer.stop();
                    isPaused = true;
                } else {
                	inGamePanel.setVisible(false);
                    timer.restart(); // Riprendi il gioco
                    Constants.isGamePaused = false;
                    isPaused = false;
                }
            }
        });
    	
        esciButton = new JButton("Esci");
        esciButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }

        });


        inGamePanel.add(closeButton);
    	inGamePanel.add(esciButton);

        add(inGamePanel , BorderLayout.WEST);
        
    	setTitle("MVC Game"); 
    	setLocationRelativeTo(null); 
    	setResizable(false); 
    	setVisible(true); 
    	setSize(Constants.MAX_WIDTH, Constants.MAX_HEIGHT); 
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        
    	if(Constants.MENUAPERTO == false) {
    	
    		//Elimina il menu e libera la memoria occupata da esso
    		menu.setVisible(false);
    		menu.dispose();
    		menu = null;
    		
    		controller = new GameController();
    		
    		add(controller.getGame());
    		setLocation(300, 100); // Imposta le coordinate manualmente
    		setFocusable(true);
    		requestFocus(); 
    		startEngine();
    	}
    	
    }

    
    
    private void startEngine() {
        addKeyListener(new KeyListener() {
            @Override 
            public void keyPressed(KeyEvent e) {
                controller.onKeyPressed(e);
                if(e.getKeyCode() == e.VK_ESCAPE) 
                {
                	inGamePanel.setVisible(true);
                	timer.stop();
                	isPaused = true;
                	Constants.isGamePaused = true; // Imposta il gioco in pausa nel GameController
                    controller.getGame().repaint(); // Aggiorna manualmente il GameView
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                controller.onKeyReleased(e);
            }
            
            @Override
            public void keyTyped(KeyEvent e) {
            }
        });
        
        timer = new Timer(5, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	if(isPaused == false) 
            	{
                controller.onTick();
                repaint();
            	}

            }  
        });

        timer.start();
    }

    /** 
     * @param args
     */
    public static void main(String[] args) {
    	menu = new MenuFrame();
    	
    }
}
