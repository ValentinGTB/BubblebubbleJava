package com.bubblebobble.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.bubblebobble.ProfilesManager;
import com.bubblebobble.models.ProfileModel;

public class ClassificationView extends JPanel {
    public ClassificationView() {
        super(new BorderLayout());
        setBackground(Color.BLACK); // Sfondo nero per coerenza con il resto dell'interfaccia
        addTitle();
        addScores();
    }

    private void addTitle() {
        JLabel titleLabel = new JLabel("Classifica", JLabel.CENTER);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 24)); // Font più grande per il titolo
        titleLabel.setForeground(Color.WHITE); // Colore bianco per visibilità su sfondo nero
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0)); // Aggiungi un po' di spazio attorno al titolo
        add(titleLabel, BorderLayout.NORTH);
    }

    private void addScores() {
        JPanel scoresPanel = new JPanel(new GridLayout(ProfilesManager.getInstance().getProfiles().size(), 2, 0, 10));
        scoresPanel.setBackground(Color.BLACK); // Sfondo nero per il pannello dei punteggi
        scoresPanel.setBorder(new EmptyBorder(10, 200, 250, 200)); // Aggiungi margini per il pannello dei punteggi
        ProfilesManager.getInstance().getProfiles()
                .stream()
                .sorted((o1, o2) -> o2.getHighestScore() - o1.getHighestScore()) // Ordine discendente
                .forEach(profile -> scoresPanel.add(createScoreButton(profile)));
        add(scoresPanel, BorderLayout.CENTER);
    }
    

    private JButton createScoreButton(ProfileModel profile) {
        JButton profileButton = new JButton(profile.getNickname() + " : " + profile.getHighestScore());
        profileButton.setFont(new Font("Monospaced", Font.PLAIN, 28)); // Font più grande per i pulsanti
        profileButton.setForeground(Color.BLACK); // Colore del testo del pulsante
        profileButton.setBackground(Color.WHITE); // Sfondo del pulsante
        profileButton.setFocusPainted(false); // Rimuovi il bordo di messa a fuoco del pulsante
        profileButton.setBorder(new EmptyBorder(10, 100, 10, 100)); // Aggiungi margini interni al pulsante
        return profileButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
