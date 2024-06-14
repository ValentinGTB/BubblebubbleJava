package com.bubblebobble.views;
import com.bubblebobble.ResourceManager;
import com.bubblebobble.models.*;
import java.awt.*;

/**
 * Questa classe permette la visualizzazione di una piattaforma nel gioco Bubble Bobble.
 * Gestisce il rendering grafico della piattaforma in base al suo modello.
 */
public class PlatformView {
    private PlatformModel model;
/**
     * Costruttore di {@code PlatformView}.
     * Inizializza la visualizzazione della piattaforma con il modello specificato.
     *
     * @param model Il modello della piattaforma.
     */
    public PlatformView(PlatformModel model) {
        this.model = model;
    }
    /**
     * Ottiene l'immagine della piattaforma corrente in base al livello del gioco.
     *
     * @return L'immagine della piattaforma.
     */
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
