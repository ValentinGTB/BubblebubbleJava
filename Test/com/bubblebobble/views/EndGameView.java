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

public class EndGameView extends JPanel {
    private BubbleBobble game;

    public EndGameView(BubbleBobble game) {
        super();
        this.game = game;
        prepare();
        setBackground(Color.BLACK);
    }

    private void prepare() {
        JButton EndGameButton = new JButton();
        EndGameButton.setIcon(new ImageIcon(Constants.BASE_URL + "/EndGame.png")); // Sostituisci con il percorso reale del tuo file
        EndGameButton.setBorderPainted(false); // Rimuove il bordo del pulsante
        EndGameButton.setContentAreaFilled(false); // Rimuove il riempimento del pulsante
        EndGameButton.setFocusPainted(false); // Rimuove il focus visivo del pulsante

        // Aggiungi un ActionListener per chiudere il gioco quando l'immagine viene cliccata
        EndGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.close();
            }
        });

        add(EndGameButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
