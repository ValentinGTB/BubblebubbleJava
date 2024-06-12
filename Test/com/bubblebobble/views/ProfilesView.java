package com.bubblebobble.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.bubblebobble.BubbleBobble;
import com.bubblebobble.Constants;
import com.bubblebobble.ProfilesManager;
import com.bubblebobble.models.GameModel;
import com.bubblebobble.models.ProfileModel;

import java.io.File;

public class ProfilesView extends JPanel {
    private BubbleBobble game;

    public ProfilesView(BubbleBobble game) {
        super();
        this.game = game;

        setBackground(Color.red);

        // aggiungi i profili alla schermata
        ProfilesManager.getInstance().getProfiles().stream().forEach(profile -> addProfile(profile));
    }

    private void addProfile(ProfileModel profile) {
        JButton profileButton = new JButton(profile.getNickname());
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameModel.getInstance().setProfile(profile);
                game.startGame();
            }
        });

        add(profileButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
