package com.bubblebobble.views;
import com.bubblebobble.Constants;
import com.bubblebobble.models.*;
import java.awt.*;


public class PlatformView {
    private PlatformModel model;
    private Image lv1Image = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "Livelli/lv1.png");


    public PlatformView(PlatformModel model) {
        this.model = model;
    }

    public void paintComponent(Graphics g) {

        // int imageWidth = lv1Image.getWidth(null);
        // int imageHeight = lv1Image.getHeight(null);

        // int numberOfImages = Constants.MAX_HEIGHT / imageWidth;

        // for (int i = 0; i <= numberOfImages; i++) {
        //     g.drawImage(lv1Image, model.getPlatformX() + i * imageWidth, model.getPlatformY(), null);
        // }

        g.setColor(Color.PINK);
        g.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
    }
}
