package com.bubblebobble;

import java.io.*;
import java.util.Random;
import java.awt.*;

public class ResourceManager {

    private Image enemyImage; 
    private Image bubbleImage;
    private Image[] fruitImages;
    private Image[] walkFrames;

    private int totalWalkFrames = 3;

    private Random random = new Random();

    private ResourceManager() {
        loadEnemyResources();
        
    }
    
    private static ResourceManager instance;
    
    public static ResourceManager getInstance() {
        if (instance == null)
            instance = new ResourceManager();

        return instance;
    }

    public Font getFont(String fontName) {
        // Carica il font personalizzato da un file TTF
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(Constants.BaseURL + fontName));
            font = font.deriveFont(Font.BOLD, 16); // Imposta la dimensione del font
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            // Gestisci l'eccezione in modo appropriato
        }
        return font;
    }

    public Font getFontOrDefault(String fontName) {
        Font font = getFont(fontName);

        if (font != null)
            return font;

        return new Font("Courier New", Font.BOLD, 32);
    }

    private void loadEnemyResources() {
        bubbleImage = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "pallanemica.png");
        enemyImage = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "Enemy1.png");

        // fruit images
        fruitImages = new Image[30];
        for (int i = 0; i < 29; i++) {
            fruitImages[i] = Toolkit.getDefaultToolkit()
                    .getImage(Constants.BaseURL + "/Frutta/Cibo-" + (i + 1) + ".png");
        }

        // walk frames
        walkFrames = new Image[totalWalkFrames];
        for (int i = 0; i < totalWalkFrames; i++) {
            walkFrames[i] = Toolkit.getDefaultToolkit().getImage(Constants.BaseURL + "Nemico_" + i + ".png");
        }
    }

    public Image[] getEnemeyWalkFrames() {
        return walkFrames;
    }

    public Image getRandomFruitImage() {
        int randomIndex = random.nextInt(fruitImages.length);
        return fruitImages[randomIndex];
    }

    public Image getEnemeyImage() {
        return enemyImage;
    }

    public Image getBubbleImage() {
        return bubbleImage;
    }
}
