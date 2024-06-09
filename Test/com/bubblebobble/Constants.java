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

    //Valore per sapere se il player è stato colpito
    public static boolean colpito = false;


    //GameSize

    public static final int WALL_LEFT = 60;
    public static final int WALL_RIGHT = 840;
    
    public static int MAX_WIDTH = 900;
    public static int MAX_HEIGHT = 800;

    //Platform

    public static int ALL_PLATFORMHEIGHT = 40;
    
    // Wall
    public static int  WallWidth = 40;


    // Player 
    
    public static int VITA = 5;
    public static int SPEED = 2;
    public static int BOOSTED_SPEED = 4;
    public static int PROJECTILE_SPEED = 10;

    //Mantieni menu aperto fin quando è true
    //Quando si avvia il gioco, questa viene messa a false
    public static boolean MENUAPERTO = true;
    
    public static boolean INGAMEMENUAPERTO = false;
    public static boolean isGamePaused = false;

    //Costante livello attuale
    public static int livelloAttuale = 0;

    //Power Up (Le costanti sono usate solo per i power up che devono uccidere il nemico e non venire più riusati per tutto il livello)

    public static boolean killthemall = false;
    public static boolean freeze = false;
    public static boolean freezeAndKill = false;

    public static final int SHOOT_DELAY = 1000 / 3;
    public static final int SHORT_SHOOT_DELAY = 1000 / 6;
}
