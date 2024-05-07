package com.bubblebobble.views;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.bubblebobble.models.*;

public class PlatformView {
    private PlatformModel model;

    public PlatformView(PlatformModel model) {
        this.model = model;
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(model.getPlatformX(), model.getPlatformY(), model.getPlatformWidth(), model.getPlatformHeight());
    }
}
