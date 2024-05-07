package com.bubblebobble;
import javax.swing.*;

import com.bubblebobble.controllers.*;
import com.bubblebobble.views.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JFrame {
    private GameController controller;

    public Main() {
        controller = new GameController();

        add(controller.getGame());
        setTitle("MVC Game");
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(Constants.MAX_WIDTH, Constants.MAX_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        setVisible(true);
        requestFocus();
        startEngine();
    }

    private void startEngine() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                controller.onKeyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                controller.onKeyReleased(e);
            }
            
            @Override
            public void keyTyped(KeyEvent e) {
            }
        });

        Timer timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onTick();
                repaint();
            }
        });

        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
