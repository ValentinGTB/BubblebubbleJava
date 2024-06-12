package com.bubblebobble;

import java.io.*;
import java.util.HashMap;
import java.awt.*;

public class ResourceManager {
    private HashMap<String, Image> images;

    private ResourceManager() {
        images = new HashMap<>();
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
            font = Font.createFont(Font.TRUETYPE_FONT, new File(Constants.BASE_URL + fontName));
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

    public Image getImage(String filename) {
        if (!images.containsKey(filename)) {
            images.put(filename, Toolkit.getDefaultToolkit().getImage(Constants.BASE_URL + filename));
        }
        
        return images.get(filename);
    }
}
