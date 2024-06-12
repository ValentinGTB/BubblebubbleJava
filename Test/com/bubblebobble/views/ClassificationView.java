package com.bubblebobble.views;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.bubblebobble.ProfilesManager;
import com.bubblebobble.models.GameModel;
import com.bubblebobble.models.ProfileModel;

public class ClassificationView extends JPanel {
    public ClassificationView() {
        super();
        addScores();
    }

    private void addScores() {
        ProfilesManager
            .getInstance()
            .getProfiles()
            .stream()
            .sorted((o1, o2) -> o1.getHighestScore() - o2.getHighestScore())
            .forEach(profile -> addScore(profile));
    }

    private void addScore(ProfileModel profile) {
        JButton profileButton = new JButton(profile.getNickname() + " " + profile.getHighestScore());
        add(profileButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
