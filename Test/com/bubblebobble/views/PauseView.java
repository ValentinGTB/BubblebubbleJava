package com.bubblebobble.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.bubblebobble.BubbleBobble;

public class PauseView extends JPanel {
    private BubbleBobble game;

    public PauseView(BubbleBobble game) {
        super();
        this.game = game;

        setBackground(Color.red);

        // prepare window
        addExitButton();
        addResumeButton();
    }

    private void addResumeButton() {
        JButton resumeButton = new JButton("Continua");
        resumeButton.addActionListener(new ActionListener() {
        
			@Override
            public void actionPerformed(ActionEvent e) {
                game.resume();
            }
        });
    	
        add(resumeButton);
    }

    private void addExitButton() {
        JButton exitButton = new JButton("Esci");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        add(exitButton);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
