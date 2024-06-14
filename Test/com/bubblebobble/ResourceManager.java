package com.bubblebobble;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
/**
 * Gestisce le risorse grafiche del gioco, come immagini e font personalizzati.
 * Utilizza un sistema di caching per migliorare le prestazioni del caricamento delle risorse.
 */
public class ResourceManager {
    private HashMap<String, Image> images;

    private ResourceManager() {
        images = new HashMap<>();
    }
    
    private static ResourceManager instance;
    /**
     * Restituisce l'unica istanza della classe ResourceManager, utilizzando il pattern singleton.
     *
     * @return L'istanza singleton di ResourceManager
     */
    public static ResourceManager getInstance() {
        if (instance == null)
            instance = new ResourceManager();

        return instance;
    }
  /**
     * Carica e restituisce un oggetto Font da un file TrueType (.ttf).
     *
     * @param fontName Il nome del file del font da caricare
     * @return Il Font caricato, oppure null se il caricamento fallisce
     */
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
    /**
     * Restituisce un oggetto Font predefinito se il caricamento del font specificato fallisce.
     *
     * @param fontName Il nome del file del font da caricare
     * @return Il Font caricato se disponibile, altrimenti un Font di fallback predefinito
     */
    public Font getFontOrDefault(String fontName) {
        Font font = getFont(fontName);

        if (font != null)
            return font;

        return new Font("Courier New", Font.BOLD, 32);
    }
    /**
     * Carica e restituisce un'immagine specificata dal filename.
     * Utilizza un sistema di caching per evitare il caricamento ripetuto delle stesse immagini.
     *
     * @param filename Il nome del file dell'immagine da caricare
     * @return L'immagine caricata
     */
    public Image getImage(String filename) {
        if (!images.containsKey(filename)) {
            images.put(filename, Toolkit.getDefaultToolkit().getImage(Constants.BASE_URL + filename));
        }
        
        return images.get(filename);
    }
}
