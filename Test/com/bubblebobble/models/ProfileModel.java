package com.bubblebobble.models;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.bubblebobble.ResourceManager;

public class ProfileModel {
    private String avatar;
    private int wonGames;
    private int lostGames;
    private String nickname;
    private String fileName;
    private int highestScore;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Image getAvatar() {
        return ResourceManager.getInstance().getImage("Avatars/" + avatar);
    }

    public void setAvatar(String fileName) {
        this.avatar = fileName;
    }

    public int getPlayedGames() {
        return wonGames + lostGames;
    }

    public int getWonGams() {
        return wonGames;
    }

    public void setWonGams(int wonGames) {
        this.wonGames = wonGames;
    }

    public int getLostGames() {
        return lostGames;
    }

    public void setLostGames(int lostGames) {
        this.lostGames = lostGames;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int score) {
        this.highestScore = score;
    }

    public void setSavePath(String fileName) {
        this.fileName = fileName;
    }

    public void save() throws IOException {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println("nickname=" + getNickname());
            writer.println("avatar=" + avatar);
            writer.println("wonGames=" + getWonGams());
            writer.println("lostGames=" + getLostGames());
            writer.println("highestScore=" + getHighestScore());
        }
    }

    public static ProfileModel loadFromFile(String fileName) throws IOException {
        ProfileModel profile = new ProfileModel();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    if (key.equals("nickname")) {
                        profile.nickname = value;
                    } else if (key.equals("avatar")) {
                        profile.avatar = value;
                    } else if (key.equals("wonGames")) {
                        profile.wonGames = Integer.parseInt(value);
                    } else if (key.equals("lostGames")) {
                        profile.lostGames = Integer.parseInt(value);
                    } else if (key.equals("highestScore")) {
                        profile.highestScore = Integer.parseInt(value);
                    }
                }
            }
            profile.setSavePath(fileName);
        }

        return profile;
    }
}
