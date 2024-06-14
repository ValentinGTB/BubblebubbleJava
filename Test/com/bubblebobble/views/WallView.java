package com.bubblebobble.views;
import com.bubblebobble.Constants;
import com.bubblebobble.ResourceManager;
import com.bubblebobble.models.*;
import java.awt.*;
/**
 * Questa classe rappresenta la visualizzazione dei muri.
 * Gestisce il disegno del muro basandosi sul modello fornito.
 */
public class WallView {
    private WallModel model;
    
    public WallView(WallModel model) {
        this.model = model;
    }

    private Image getWallImage(int level) {
        return ResourceManager.getInstance().getImage("Livelli/lv" + level + ".png");
    }
    
    public void paintComponent(Graphics g) {
        Image image = getWallImage(GameModel.getInstance().getCurrentLevel().getLevel());

        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        int numberOfImages = Constants.MAX_HEIGHT / imageWidth;

        for (int i = 0; i <= numberOfImages; i++) {
            g.drawImage(image, model.getX(), model.getY() + i * imageHeight, null);
        }
    }
}
