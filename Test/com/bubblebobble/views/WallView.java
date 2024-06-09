package com.bubblebobble.views;
import com.bubblebobble.Constants;
import com.bubblebobble.models.*;
import java.awt.*;

public class WallView {
    private WallModel model;
    private Image lv1Image = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "Livelli/lv1.png");
    
    public WallView(WallModel model) {
        this.model = model;
    }
    
    public void paintComponent(Graphics g) {
        int imageWidth = lv1Image.getWidth(null);
        int imageHeight = lv1Image.getHeight(null);
        

        int numberOfImages = Constants.MAX_HEIGHT / imageWidth;

        for (int i = 0; i <= numberOfImages; i++) {
            g.drawImage(lv1Image, model.getX(), model.getY() + i * imageHeight, null);
        }

        //g.drawImage(lv1Image,model.getWallX(), model.getWallY(), null);
    }
}
