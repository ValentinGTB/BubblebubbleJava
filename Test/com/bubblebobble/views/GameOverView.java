package com.bubblebobble.views;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.bubblebobble.BubbleBobble;

public class GameOverView extends JPanel {
    private BubbleBobble game;

    public GameOverView(BubbleBobble game) {
        super();
        this.game = game;
        prepare();
    }

    private void prepare() {
        JButton profileButton = new JButton("GAME VOER");
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.close();
            }
        });

        add(profileButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
