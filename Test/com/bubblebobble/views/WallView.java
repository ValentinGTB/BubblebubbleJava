package com.bubblebobble.views;
import com.bubblebobble.Constants;
import com.bubblebobble.models.*;
import java.awt.*;

public class WallView {
    private WallModel model;
    private Image image = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "Livelli/lv" + GameModel.getInstance().getCurrentLevel().getLevel() + ".png");
    
    public WallView(WallModel model) {
        this.model = model;
    }
    
    public void paintComponent(Graphics g) {
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        int numberOfImages = Constants.MAX_HEIGHT / imageWidth;

        for (int i = 0; i <= numberOfImages; i++) {
            g.drawImage(image, model.getX(), model.getY() + i * imageHeight, null);
        }
    }
}
