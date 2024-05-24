package com.bubblebobble.views;
import com.bubblebobble.models.*;
import java.awt.*;


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
