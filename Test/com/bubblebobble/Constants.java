package com.bubblebobble;
/**
 * Contiene diverse costanti necessarie al funzionamento del gioco.
 * Le costanti presenti variano da immagini a interi per le dimensioni dei muri a booleane usate per i menu...
 */
public class Constants {
    // === Common
    public static final String FONT_NAME = "arcade-legacy.ttf";
    /**
     * URL di base messo all'inizio di ogni percorso per caricare l'immagine. 
     * L'utilizzo è: BaseURL + "nomeImmagine.png" per accedere alle immagini 
     * salvate in questo percorso.
     */
    public static String BASE_URL = "Test/com/bubblebobble/views/img/";
    /**
     * URL di base per i profili dei giocatori.
     */
    public static String PROFILES_URL = "Test/com/bubblebobble/profiles/";
    
    // === Window
    /**
     * Larghezza massima, usata in diversi casi per impostare la misura di piattaforme o altri
     * elementi. Principalmente è usata per definire le dimensioni della finestra.
     */
    public static int MAX_WIDTH = 1000;
    /**
     * Altezza massima, usata in diversi casi per impostare la misura di piattaforme o altri
     * elementi. Principalmente è usata per definire le dimensioni della finestra.
     */
    public static int MAX_HEIGHT = 800;
    
    // === Player
    public static final int PLAYER_WIDTH = 40;
    public static final int PLAYER_HEIGHT = 40;
    /**
    * Numero di vite iniziali del giocatore.
    */
    public static final int PLAYER_LIVES = 4;
    public static final int PLAYER_DEFAULT_SPEED = 3;
    /**
    * Velocità di movimento potenziata del giocatore.
    */
    public static final int PLAYER_BOOSTED_SPEED = 5;

    public static final int PROJECTILE_SPEED = 10;
    public static final int SHOOT_DELAY = 1000 / 3;
    public static final int SHORT_SHOOT_DELAY = 1000 / 6;
    
    // === Enemy
    public static final int ENEMY_WIDTH = 40;
    public static final int ENEMY_HEIGHT = 40;
    /**
    * Velocità di movimento del nemico.
    */
    public static final int ENEMY_SPEED = 2;
    public static final int ENEMY_BUBBLE_SPEED = 2;

    // === Platform
    public static final int PLATFORM_WIDTH = 40;
    public static final int PLATFORM_HEIGHT = 40;

    // === Wall
    public static final int WALL_WIDTH = 40;
    public static final int WALL_HEIGHT = 40;
}
