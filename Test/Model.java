import javax.swing.ImageIcon;

public class Model {
    
    private int DISTANCEPG = 40;
    private int MAXHEIGHT = 800;
    private int MAXWIDTH = 800;
    //Froza di Gravità
    private int x = MAXWIDTH/2;
    private int y = (MAXHEIGHT*90)/100;
    private int xSpeed = 0;
    private int ySpeed = 0;
    
    private double gravity = 1;
    private static boolean isJumping;


    // Piattaforma 
    //----------------------------------
    private int platformWidth = MAXWIDTH;
    private int platformHeight = (MAXHEIGHT * 3) / 100 ;
    private int platformX = 0;
    private int platformY = (MAXHEIGHT*90)/100 ;
    //-----------------------------------


    // Punteggio
    //-------------------------------------

    // private int Punteggio = 0;

    //-------------------------------------


    
    //#region---------------------------------------------------------------------------------------------
    
    // Array di immagini per l'animazione della camminata
    private int frameCount = 0; // Conteggio dei frame
    private ImageIcon[] walkFrames;
    private int currentFrame = 0; // Frame corrente dell'animazione
    private int totalframe = 4;

    public Model() {
        // Carica le immagini dell'animazione della camminata
        walkFrames = new ImageIcon[totalframe]; // Supponiamo di avere 4 frame di animazione
        for (int i = 0; i < totalframe; i++) {
            walkFrames[i] = new ImageIcon(getClass().getResource("walk__" + i + ".png"));
        }
    }

    // Restituisce l'immagine corrente per l'animazione della camminata
    public ImageIcon getCurrentWalkFrame() {
        return walkFrames[currentFrame];
    }

    //---------------------------------------------------------------------------------------------


    // Restituisce la posizione x del personaggio
    public int getX() {
        return x;
    }

    // Restituisce la posizione y del personaggio
    public int getY() {
        return y;
    }

    public int getMAXWIDTH(){
        return MAXWIDTH;
    }

    public int getMAXHEIGHT(){
        return MAXHEIGHT;
    }

    // Restituisce la posizione x della piattaforma
    public int getPlatformX() {
        return platformX;
    }

    // Restituisce la posizione y della piattaforma
    public int getPlatformY() {
        return platformY;
    }

    // Restituisce la larghezza della piattaforma
    public int getPlatformWidth() {
        return platformWidth;
    }

    // Restituisce l'altezza della piattaforma
    public int getPlatformHeight() {
        return platformHeight;
    }

    // Muove il personaggio
    public void move() {
        x += xSpeed;
        y += ySpeed;
        ySpeed += gravity;

        // private int platformX = 0;
        // private int platformY = 230;
        // private int platformWidth = 400;
        // private int platformHeight = 20;

        // Tracciamento tocco della piattaforma



        if (y + DISTANCEPG >= platformY && y <= platformY + platformHeight && x + DISTANCEPG >= platformX && x <= platformX + platformWidth) {
            y = platformY - DISTANCEPG; // Set della posizione del personaggio su piattaforma
            ySpeed = 0;
            isJumping = false; // riconosce se il drago atterra sulla piattaforma per non farlo più saltare 
            }

        if (x < 0) {
            x = 0;
        }

        if (x > MAXWIDTH - 70) {
            x = MAXWIDTH - 70;
        }

        if (y < 0) {
            y = 0;
        }

        if (y > MAXHEIGHT - 70) {
            y = -MAXHEIGHT;
        }


        if (xSpeed != 0) {
            frameCount++;
            
            // Aggiorna l'animazione ogni 5 frame (puoi regolare questo valore per rallentare o accelerare l'animazione)
            if (frameCount % 5 == 0) {
                currentFrame = (currentFrame + 1) % 4;
            }
        } else {
            frameCount = 0;
        }

        
        
    }

    public void salta() {
        if (!isJumping) {
            ySpeed = -15; 
            isJumping = true; 
        }
    } // risolto problema salto 


    public void setMAXWIDTH(int MAXWIDTH){
        this.MAXWIDTH = MAXWIDTH;
    }

    public void setMAXHEIGHT(int MAXHEIGHT){
        this.MAXHEIGHT = MAXHEIGHT;
    }
    
    // Imposta la velocità x del personaggio
    public void setXSpeed(int speed) {
        xSpeed = speed;
    }

    // Imposta la velocità y del personaggio
    public void setYSpeed(int speed) {
        ySpeed = speed;
    }

    // Imposta la velocità y del personaggio manualmente
    public void setYSpeedManual(int speed) {
        ySpeed = speed;
    }

    // Imposta la posizione della piattaforma
    public void setPlatformPosition(int x, int y, int width, int height) {
        platformX = x;
        platformY = y;
        platformWidth = width;
        platformHeight = height;
    }

    

    
	

}
