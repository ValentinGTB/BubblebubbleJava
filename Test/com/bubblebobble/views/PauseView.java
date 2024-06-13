package com.bubblebobble.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.bubblebobble.BubbleBobble;

public class PauseView extends JPanel {
    private BubbleBobble game;

    public PauseView(BubbleBobble game) {
        super();
        this.game = game;

        setBackground(Color.BLACK); // Cambia il colore dello sfondo

        // Imposta il layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 30, 30, 30); // Margini intorno ai pulsanti

        // Aggiungi i pulsanti
        addResumeButton(gbc);
        addExitButton(gbc);
    }

    private void addResumeButton(GridBagConstraints gbc) {
        JButton resumeButton = new JButton("Continua");
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.resume();
            }
        });

        // Personalizza il pulsante
        resumeButton.setBackground(Color.WHITE);
        resumeButton.setForeground(Color.BLACK);
        resumeButton.setFocusPainted(false);
        resumeButton.setBorder(new LineBorder(Color.BLACK, 1));

        // Imposta la dimensione preferita e il font
        resumeButton.setPreferredSize(new Dimension(200, 80));
        resumeButton.setFont(new Font("Arial", Font.BOLD, 20));

        // Posiziona il pulsante al centro
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(resumeButton, gbc);
    }

    private void addExitButton(GridBagConstraints gbc) {
        JButton exitButton = new JButton("Esci");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Personalizza il pulsante
        exitButton.setBackground(Color.WHITE);
        exitButton.setForeground(Color.BLACK);
        exitButton.setFocusPainted(false);
        exitButton.setBorder(new LineBorder(Color.BLACK, 1));

        // Imposta la dimensione preferita e il font
        exitButton.setPreferredSize(new Dimension(200, 80));
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));

        // Posiziona il pulsante sotto il pulsante "Continua"
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(exitButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
