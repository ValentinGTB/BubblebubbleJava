package com.bubblebobble;

import java.awt.*;
import javax.imageio.ImageIO;


public class Constants {

    public static final String FONT_NAME = "arcade-legacy.ttf";

    public static Image immagineBottoneInizia;
    public static Image immagineBottoneEsci;

    public void inizializzaConstants()
    {
        try{
				immagineBottoneInizia = ImageIO.read(getClass().getResource("imageButtonStart.png"));
				immagineBottoneEsci = ImageIO.read(getClass().getResource("exitButtonDef.png"));
                

				}
		catch(Exception e)
				{
					System.out.print("errore nel caricamento immagine" + e);
				}
    }

    //COSTANTI PER LE IMMAGINI

    public static String BaseURL = "Test/com/bubblebobble/views/img/";
    //Percorso per l'immagine di start nel menu
    public static String IMMAGINESTART =  BaseURL + "start.png";
    public static String IMMAGINESTART2 =  BaseURL + "start2.png";

    
    //Mantieni menu aperto fin quando è true
    //Quando si avvia il gioco, questa viene messa a false
    public static boolean MENUAPERTO = true;
    
    public static boolean INGAMEMENUAPERTO = false;
    public static boolean isGamePaused = false;
    






    // === Window
    public static int MAX_WIDTH = 1000;
    public static int MAX_HEIGHT = 800;
    
    // === Player
    public static final int PLAYER_WIDTH = 40;
    public static final int PLAYER_HEIGHT = 40;
    
    public static final int PLAYER_LIVES = 5;
    public static final int PLAYER_DEFAULT_SPEED = 3;
    public static final int PLAYER_BOOSTED_SPEED = 5;

    public static final int PROJECTILE_SPEED = 10;
    public static final int SHOOT_DELAY = 1000 / 3;
    public static final int SHORT_SHOOT_DELAY = 1000 / 6;
    
    // === Enemy
    public static final int ENEMY_WIDTH = 40;
    public static final int ENEMY_HEIGHT = 40;

    public static final int ENEMY_SPEED = 2;
    public static final int ENEMY_BUBBLE_SPEED = 2;

    // === Platform
    public static final int PLATFORM_WIDTH = 40;
    public static final int PLATFORM_HEIGHT = 40;

    // === Wall
    public static final int WALL_WIDTH = 40;
    public static final int WALL_HEIGHT = 40;
}
