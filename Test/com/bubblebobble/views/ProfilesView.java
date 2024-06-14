package com.bubblebobble.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
 import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bubblebobble.BubbleBobble;
import com.bubblebobble.ProfilesManager;
import com.bubblebobble.models.GameModel;
import com.bubblebobble.models.ProfileModel;
/**
 * Questa classe permette di visualizzare il menu per la selezione dei profili.
 * Estende JPanel e organizza l'interfaccia utilizzando BorderLayout.
 * Include un titolo, una lista di profili selezionabili e una classifica.
 */
public class ProfilesView extends JPanel {
    private BubbleBobble game;
    /**
     * Costruttore della classe ProfilesView.
     * Inizializza la vista dei profili con il gioco specificato.
     *
     * @param game L'istanza del gioco Bubble Bobble.
     */
    public ProfilesView(BubbleBobble game) {
        super(new BorderLayout()); // Layout BorderLayout per organizzare i componenti
        this.game = game;
        
        setBackground(Color.BLACK);
        
        // Pannello per l'etichetta "Scegli profilo" al centro superiore
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("Scegli profilo");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(90, 0, 0, 0));

        // Aggiungi l'etichetta al BorderLayout.NORTH del JPanel principale
        add(topPanel, BorderLayout.NORTH);

        // Pannello per i profili degli account
        JPanel profilesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        profilesPanel.setBackground(Color.BLACK);
        ProfilesManager.getInstance().getProfiles().forEach(profile -> addProfile(profile, profilesPanel));
        
        // Aggiungi il pannello dei profili al BorderLayout.CENTER del JPanel principale
        add(profilesPanel, BorderLayout.CENTER);

        // Aggiungi la classifica alla fine usando il metodo esistente
        addClassification();
    }
    /**
     * Aggiunge un pulsante di selezione per un profilo specificato nel pannello dei profili.
     *
     * @param profile Il profilo da aggiungere.
     * @param profilesPanel Il pannello in cui aggiungere il pulsante del profilo.
     */
    private void addProfile(ProfileModel profile, JPanel profilesPanel) {
        JButton profileButton = new JButton();
        profileButton.setLayout(new BorderLayout()); // Utilizza BorderLayout per posizionare l'immagine sopra il testo
    
        // Icona dell'avatar sopra il testo
        ImageIcon profileIcon = new ImageIcon(profile.getAvatar());
        JLabel iconLabel = new JLabel(profileIcon);
        profileButton.add(iconLabel, BorderLayout.CENTER); // Aggiunge l'immagine al centro (sopra il testo)
    
        // Testo del nome al centro
        JLabel nameLabel = new JLabel(profile.getNickname());
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setHorizontalAlignment(JLabel.CENTER); // Centra il testo orizzontalmente
        profileButton.add(nameLabel, BorderLayout.SOUTH); // Aggiunge il testo sotto l'immagine
    
        profileButton.setBackground(Color.WHITE);
        profileButton.setFocusPainted(false);
        profileButton.setPreferredSize(new Dimension(160, 160)); // Imposta dimensioni prefissate per uniformità
    
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameModel.getInstance().setProfile(profile);
                game.startGame();
            }
        });
    
        profilesPanel.add(profileButton);
    }
    /**
     * Visualizza la classifica alla fine dei profili.
     */
    private void addClassification() {
        // Aggiungi la classifica usando il metodo esistente senza modificarla
        ClassificationView classificationView = new ClassificationView();
        add(classificationView, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Possibile aggiungere disegni personalizzati qui
    }
}
