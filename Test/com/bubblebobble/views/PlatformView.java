package com.bubblebobble.views;
import com.bubblebobble.Constants;
import com.bubblebobble.ResourceManager;
import com.bubblebobble.models.*;
import java.awt.*;


public class PlatformView {
    private PlatformModel model;

    public PlatformView(PlatformModel model) {
        this.model = model;
    }

    private Image getPlatformImage() {
        return ResourceManager.getInstance().getImage("Livelli/lv" + GameModel.getInstance().getCurrentLevel().getLevel() + ".png");
    }

    public void paintComponent(Graphics g) {
        Image image = getPlatformImage();

        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        int numberOfImages = model.getWidth() / imageWidth;

        int widthToFill = model.getWidth();
        for (int i = 0; i <= numberOfImages; i++) {
            int width = Math.min(widthToFill, imageWidth);
            widthToFill -= width;
            g.drawImage(image, model.getX() + i * imageWidth, model.getY(), width, imageHeight, null);
        }
    }
}
