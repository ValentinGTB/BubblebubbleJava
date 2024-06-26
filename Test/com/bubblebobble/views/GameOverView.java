package com.bubblebobble.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import com.bubblebobble.BubbleBobble;
import com.bubblebobble.Constants;
/**
 * Questa classe rappresenta la visualizzazione della schermata di Game Over nel gioco.
 * Mostra un'immagine di Game Over e permette di chiudere il gioco quando l'immagine viene cliccata.
 */
public class GameOverView extends JPanel {
    private BubbleBobble game;
    /**
     * Costruttore di GameOverView.
     */
    public GameOverView(BubbleBobble game) {
        super();
        this.game = game;
        prepare();
        setBackground(Color.BLACK);
    }
    /**
     * Prepara la visualizzazione di Game Over aggiungendo un pulsante con l'immagine di Game Over.
     * Il pulsante chiude il gioco quando viene cliccato.
     */
    private void prepare() {
        // Crea un JButton senza testo per l'immagine
        JButton gameOverButton = new JButton();
        gameOverButton.setIcon(new ImageIcon(Constants.BASE_URL + "/GameOver2.png")); // Sostituisci con il percorso reale del tuo file
        gameOverButton.setBorderPainted(false); // Rimuove il bordo del pulsante
        gameOverButton.setContentAreaFilled(false); // Rimuove il riempimento del pulsante
        gameOverButton.setFocusPainted(false); // Rimuove il focus visivo del pulsante

        // Aggiungi un ActionListener per chiudere il gioco quando l'immagine viene cliccata
        gameOverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.close();
            }
        });

        add(gameOverButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Puoi aggiungere eventuali disegni personalizzati qui se necessario
    }
}