package com.bubblebobble.models;

import com.bubblebobble.Constants;
import java.util.ArrayList;

public class EnemyModel extends PlayerModel {
    private boolean inBubble;
    PlayerModel pm;
    ArrayList<WallModel> walls;

    private static final int DISTANZA_SALTO_X = 50; // Ad esempio, 50 pixel
    
    private Constants costanti = new Constants();

    boolean isEaten = false;
    boolean isFruit = false;
    boolean noMuro = true;
    boolean spostaSx, spostaDx;
    boolean colliding = false;
    boolean inAlto = false;
    int xEnemy = 70;
    int yEnemy = 680;
    int DISTANCEPG = 45;
    double enemySpeed = Constants.SPEED;
    int xGiocatore;
    int yGiocatore;
    int flipFlop = 0;

    public EnemyModel(PlayerModel pm , ArrayList<WallModel> walls) {
        this.pm = pm;
        this.walls = walls;
        this.inBubble = false;
        MyThread.getInstance().startThread(pm);
    }

    public void move() {

        if(!inBubble){
            if (xEnemy+1 != xGiocatore || yEnemy-1 != yGiocatore-4) {
                int deltaX = xGiocatore - xEnemy;
                int deltaY = yGiocatore - yEnemy;
                distance(deltaX, deltaY);
                Constants.colpito = false;
                flipFlop = 1;

            } else {

                /*
                * if(flipFlop == 1) ------ POSSIBILE LOGICA DA POTER USARE PER FAR COLPIRE
                * SUBITO UNA VOLTA SOLA IL GIOCATORE E POI PASSARE L'AZIONE AL THREAD
                * {
                * flipFlop = 0;
                * }
                */
                Constants.colpito = true;
                // vanish del personaggio per 3 secondi player.vanish(3, 3) sta per 3 secondi e
                // il frame scompare e riappare per 3 secondi.
            }
        }
    }

    public void collisionDead()
    {
        // to fix: X e Y di enemy e player sono prese in modo poco preciso
        if (xEnemy == xGiocatore || yEnemy == yGiocatore && isFruit == false)
        {
            isFruit = true;
        }

        //Se X e Y di giocatore e nemico coincidono e sono un frutto allora il giocatore mangia il nemico
        if (xEnemy == xGiocatore || yEnemy == yGiocatore && isFruit == true)
        {
            System.out.println("MANGIATO");
            isEaten = true;
            Constants.livelloAttuale = 1;
        }

    }


    public boolean isFruit(){return isFruit;}
    public boolean isEaten(){return isEaten;}

    public void setInBubble(boolean inBubble) {
        this.inBubble = inBubble;
    }

    public boolean isInBubble() {
        return inBubble;
    }

    public void distance(int deltaX, int deltaY) {
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY); // Formula per avvicinare il nemico al giocatore

        // Normalizza il vettore
        double directionX = deltaX / distance;
        // Aggiorna la posizione del nemico
        if (colliding) {
            if(!inAlto) {
                xEnemy += directionX * enemySpeed;
            }

            if (inAlto) {
                
                // Logica per far saltare il nemico lungo la piattaforma
                // Logica per far saltare il nemico lungo la piattaforma
                if(checkY(yGiocatore, yEnemy))
                { 
                    int distanzaX = Math.abs(xGiocatore - xEnemy);                    
                    // Se sei già distante dal giocatore orizzontalmente
                    if (distanzaX >= DISTANZA_SALTO_X) {
                        // Infine faccio salire il nemico
                        yEnemy -= (enemySpeed + 1);
                        //Se il nemico non è più sotto il giocatore, non deve più spostarsi a sinistra quindi = false
                        spostaSx = false;
                    }
                    // Se sei perfettamente sotto il giocatore
                    else
                    {
                        //Se c'è un muro a destra...
                        if(controllaMuro())
                        {
                            //Metto spostaSx a true per iniziare lo spostamento
                            spostaSx = true;  
                            noMuro = false;      
                        }
                        else if(controllaMuroSx()) {
                            spostaDx = true;
                            noMuro = false;
                        } 
                        else {
                            if(noMuro && !spostaSx && !spostaDx) xEnemy += 1;
                        }

                        //Finché il nemico è sotto il player, sposta a sinistra
                        if(spostaSx) xEnemy -= 1;
                        if(spostaDx) xEnemy += 1;
                    }
                }
            }
        }

        // TODO: Se c'è un ostacolo lo salto aggiornando anche le y

        // Se il nemico è sopra di me, dovrò saltare

    }

    @Override
    public boolean collidesWith(PlatformModel platform) {
        // Controlla se il personaggio è sopra la piattaforma e se la parte inferiore
        // del personaggio è al di sopra del punto superiore della piattaforma

        if (enemySpeed >= 0
                && yEnemy + DISTANCEPG >= platform.getPlatformY()
                && yEnemy <= platform.getPlatformY() + platform.getPlatformHeight()
                && xEnemy + DISTANCEPG >= platform.getPlatformX()
                && xEnemy <= platform.getPlatformX() + platform.getPlatformWidth()
                && yEnemy + DISTANCEPG <= platform.getPlatformY() + platform.getPlatformHeight()) {
            // La parte inferiore del personaggio è sopra il punto superiore della
            // piattaforma
            // La parte superiore del personaggio è al di sotto della piattaforma

            // Regola la posizione del personaggio per farlo rimanere sulla piattaforma
            yEnemy = platform.getPlatformY() - DISTANCEPG;

            // --- Se sono a terra, controllo se il giocatore sta più sopra di me ---
            // Il -20 serve a rimediare alla distanza di Y tra player e enemy
           
            if (checkY(yGiocatore, yEnemy)) {
                // --- Controllo se sono alla fine della piattaforma ---
                // Sottraggo 35 di offset per far pensare all'enemy di essere a fine piattaforma
                // prima di arrivare al margine vero
                if(pm.isJumping() == false) 
                {
                    inAlto = true;
                }

                // Se sono alla fine della piattaforma
                if (xEnemy == platform.getPlatformWidth() - 15) {}
   
            }
            // --- Altrimenti, se il giocatore è sulla mia stessa piattaforma ---
            else
            {
                if(pm.isJumping() == false)
                    //System.out.println("falso, non sei in alto" + (yEnemy-35) + " " + yGiocatore);
                    inAlto = false;
            }
            

            return true; // Collisione rilevata
        }

        // Sezione di codice ragginuta SOLO SE il nemico non è più a terra

        return false; // Nessuna collisione rilevata
    }

    // Controlla muro sinistro
    public boolean controllaMuroSx() {
        boolean muroCheck = false;

        for(WallModel wm : walls) {
            // Se la X del nemico - 10 è minore o uguale alla X del muro
            if(xEnemy - 40 <= wm.getWallX() && wm.getWallX() <= 0) { 
                muroCheck = true;
            }
        }

        return muroCheck;
    }


    //Controlla muro destro
    public boolean controllaMuro()
    {
        boolean muroCheck = false;

        for(WallModel wm : walls)
        {
            //Se la X del nemico + 10 è maggiore o uguale alla X del muro (che è 845) e la X del muro non è 0 (per escludere il muro iniziale)...
            if(xEnemy + 40 >= wm.getWallX() && wm.getWallX() != 0) { 
                muroCheck = true;
            }
        }

        return muroCheck;
    }

    public boolean checkY(int yGiocatore , int yEnemy)
    {
        if (yEnemy - 35 > yGiocatore) return true;
        else return false;
    }

    public void setPlayerX(int x) {
        this.xGiocatore = x;
    }

    public void setPlayerY(int y) {
        this.yGiocatore = y;
    }

    public int getPlayerXEnemy() {
        return xGiocatore;
    }

    public int getPlayerYEnemy() {
        return yGiocatore;
    }

    public int getEnemyX() {
        return xEnemy;
    }

    public int getEnemyY() {
        return yEnemy;
    }

    public void setEnemyY(int yEnemy) {
        this.yEnemy = yEnemy;
    }

    public void setEnemyX(int xEnemy) {
        this.xEnemy = xEnemy;
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }
    public boolean getColliding(){return colliding;}

    public double getEnemySpeed() {
        return enemySpeed;
    }
    
    public void setEnemySpeed(int enemySpeed)
    {
        this.enemySpeed = enemySpeed;
    }

}