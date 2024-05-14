package com.bubblebobble;

import java.awt.Image;
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

    //Percorso per l'immagine di start nel menu
    public static String IMMAGINESTART = "C:/Users/tbone/OneDrive/Documenti/UNIVERSITA/BubblebubbleJava/BubblebubbleJava/Test/com/bubblebobble/views/img/start.png";
    public static String IMMAGINESTART2 = "C:/Users/tbone/OneDrive/Documenti/UNIVERSITA/BubblebubbleJava/BubblebubbleJava/Test/com/bubblebobble/views/img/start2.png";



    //GameSize
    
    public static int MAX_WIDTH = 900;
    public static int MAX_HEIGHT = 800;


    //Platform

    public static int ALL_PLATFORMHEIGHT = 40;
    
    // Player 
    
    public static int SPEED = 2;

    //Mantieni menu aperto fin quando è true
    //Quando si avvia il gioco, questa viene messa a false
    public static boolean MENUAPERTO = true;
    
    public static boolean INGAMEMENUAPERTO = false;
    public static boolean isGamePaused = false;

}
