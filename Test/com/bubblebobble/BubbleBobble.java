package com.bubblebobble;

import javax.swing.*;

import com.bubblebobble.controllers.*;
import com.bubblebobble.views.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BubbleBobble extends JFrame {

    // stato del gioco
    private Timer timer;
    private boolean isPaused = false;
    private GameController controller;

    // pannelli gioco
    private MenuView menuPanel;
    private GameView gamePanel;
    private JPanel currentPanel;
    private PauseView pausePanel;
    private EndGameView endGamePanel;
    private GameOverView gameOverPanel;
    private ProfilesView profilesPanel;

    public void showProfiles() {
        if (profilesPanel == null) {
            profilesPanel = new ProfilesView(this);
            add(profilesPanel, BorderLayout.CENTER);
        }

        // mostra il gioco in pausa
        showPanel(profilesPanel);

    }

    public void startGame() {
        // rimuovi menu
        menuPanel.setVisible(false);
        remove(menuPanel);
        menuPanel = null;

        // avvia controller gioco
        controller = new GameController(this);
        controller.startGame();

        gamePanel = controller.getGame();
        showPanel(gamePanel);
        add(gamePanel);
        
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                controller.onKeyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (!isPaused) pause();
                    else resume();
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
                if (!isPaused) {
                    controller.onTick();
                    repaint();
                }
            }
        });

        timer.start();
    }

    public void resume() {
        // ripristina la finestra di gioco
        showPanel(gamePanel);

        // ripristinato lo stato del gioco
        isPaused = false;
        timer.restart();
    }

    public void pause() {
        if (pausePanel == null) {
            pausePanel = new PauseView(this);
            add(pausePanel, BorderLayout.CENTER);
        }

        // mostra il gioco in pausa
        showPanel(pausePanel);

        // imposta lo stato del gioco in pausa
        isPaused = true;
        timer.stop();
    }

    public void showMenu() {
        if (menuPanel == null) {
            menuPanel = new MenuView(this);
            add(menuPanel);
        }
        
        showPanel(menuPanel);
    }

    public void showGameOver() {
        if (gameOverPanel == null) {
            gameOverPanel = new GameOverView(this);
            add(gameOverPanel);
        }

        showPanel(gameOverPanel);
    }

    public void showEndGame() {
        if (endGamePanel == null) {
            endGamePanel = new EndGameView(this);
            add(endGamePanel);
        }

        showPanel(endGamePanel);
    }

    private void showPanel(JPanel panel) {
        if (currentPanel != null) {
            currentPanel.setVisible(false);
        }

        panel.setVisible(true);
        currentPanel = panel;
    }

    public void close() {
        System.exit(0);
    }

    public void run() {
        // configura la finestra
        setResizable(false);
        setFocusable(true);
        setTitle("BubbleBobble");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.MAX_WIDTH + 15, Constants.MAX_HEIGHT + 38);
        setLocation(300, 100);

        // all'avvio del gioco, mostriamo il menu
        showMenu();
        
        // mostra il gioco
        setVisible(true);
        requestFocus();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        var app = new BubbleBobble();
        app.run();
    }
}
