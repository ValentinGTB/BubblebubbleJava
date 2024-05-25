package com.bubblebobble;

import com.bubblebobble.Constants;
import java.awt.*;
import javax.imageio.ImageIO;


public class Constants {

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
    
    public static int MAX_WIDTH = 900;
    public static int MAX_HEIGHT = 800;


    //Platform

    public static int ALL_PLATFORMHEIGHT = 40;
    
    // Wall
    public static int  WallWidth = 40;


    // Player 
    
    public static int SPEED = 2;

    //Mantieni menu aperto fin quando è true
    //Quando si avvia il gioco, questa viene messa a false
    public static boolean MENUAPERTO = true;
    
    public static boolean INGAMEMENUAPERTO = false;
    public static boolean isGamePaused = false;

}
