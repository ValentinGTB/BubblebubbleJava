package com.bubblebobble.views;
import com.bubblebobble.models.*;
import java.awt.*;

public class WallView {
    private WallModel model;

    public WallView(WallModel model) {
        this.model = model;
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(model.getWallX(), model.getWallY(), model.getWallWidth(), model.getWallHeight());
    }
}
