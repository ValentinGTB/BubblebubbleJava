package com.bubblebobble.models;

import com.bubblebobble.Constants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.IntStream;

import javax.swing.Timer;

public class EnemyModel extends CharacterModel {
    private boolean inBubble;
    PlayerModel pm;
    ArrayList<WallModel> walls;

    private static final int DISTANZA_SALTO_X = 50; // Ad esempio, 50 pixel

    boolean isEaten = false;
    boolean isFruit = false;
    boolean noMuro = true;
    boolean spostaSx, spostaDx;
    boolean colliding = false;
    boolean inAlto = false;
    int yEnemy;
    int DISTANCEPG = 45;
    double enemySpeed = Constants.SPEED;
    int flipFlop = 0;
    ArrayList<PlatformModel> platformArray;

    private Timer bubbleTimer;

    public EnemyModel(PlayerModel pm, ArrayList<WallModel> walls, ArrayList<PlatformModel> platformArray, int x,
            int y) {
        super(x, y);

        this.pm = pm;
        this.walls = walls;
        this.inBubble = false;
        this.platformArray = platformArray;
        // MyThread.getInstance().startThread(pm);
    }

    public void move() {
        super.move();

        boolean diffX = (getX() + 1 != pm.getX());
        boolean diffY = (getY() != pm.getY() - 3);

        if (!inBubble) {
            if (diffX || diffY) {
                int deltaX = pm.getX() - getX();
                int deltaY = pm.getY() - getY();
                distance(deltaX, deltaY);
                Constants.colpito = false;
                flipFlop = 1;
            }
        }

        if (!Constants.killthemall) {
            if (!inBubble) {
                if (!Constants.freeze) {
                    if (diffX || diffY) {
                        int deltaX = pm.getX() - getX();
                        int deltaY = pm.getY() - getY();
                        distance(deltaX, deltaY);
                        Constants.colpito = false;
                        flipFlop = 1;
                    } else {
                        Constants.colpito = true;
                    }
                }
                if (Constants.freezeAndKill) {
                    Constants.freeze = true;
                    boolean isInRangeX = IntStream.rangeClosed(getX() - 10, getX() + 10).anyMatch(n -> n == pm.getX());
                    if (isInRangeX) {
                        instaKill();
                    }
                }
                // else {

                /*
                 * if(flipFlop == 1) ------ POSSIBILE LOGICA DA POTER USARE PER FAR COLPIRE
                 * SUBITO UNA VOLTA SOLA IL GIOCATORE E POI PASSARE L'AZIONE AL THREAD
                 * {
                 * flipFlop = 0;
                 * }
                 */
                // Constants.colpito = true;
                // vanish del personaggio per 3 secondi player.vanish(3, 3) sta per 3 secondi e
                // il frame scompare e riappare per 3 secondi.
                // }
            }
        } else
            instaKill();
    }

    public void collisionDead() {

    }

    public boolean isFruit() {
        return isFruit;
    }

    public void setFruit(boolean value) {
        isFruit = value;

        if (isFruit && inBubble)
            setInBubble(false);
    }

    public boolean isEaten() {
        return isEaten;
    }

    public void setEaten(boolean value) {
        isEaten = value;
    }

    public void setInBubble(boolean inBubble) {
        this.inBubble = inBubble;

        if (inBubble && isFruit)
            setFruit(false);

        if (inBubble && bubbleTimer == null) {
            bubbleTimer = new Timer(6000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setFruit(true);
                    setInBubble(false);
                }
            });
            bubbleTimer.start();
        } else if (!inBubble && bubbleTimer != null) {
            bubbleTimer.stop();
        }
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
            if (!inAlto) {
                setX(getX() + (int) (directionX * enemySpeed));
            }

            if (inAlto) {
                // Logica per far saltare il nemico lungo la piattaforma

                if (checkY(pm.getY(), getY())) {
                    boolean havePlatOnTop = false;
                    int distanzaX = Math.abs(pm.getX() - getX());
                    havePlatOnTop = isPlatformAbove();

                    if (havePlatOnTop) {
                        // Se sei già distante dal giocatore orizzontalmente
                        if (distanzaX >= DISTANZA_SALTO_X) {

                            // Infine faccio salire il nemico
                            setY(getY() - (int) (enemySpeed + 4));
                            // Se il nemico non è più sotto il giocatore, non deve più spostarsi a sinistra
                            // quindi = false
                            spostaSx = false;
                        }
                        // Se sei perfettamente sotto il giocatore
                        else {
                            // Se c'è un muro a destra...
                            if (controllaMuro()) {
                                // Metto spostaSx a true per iniziare lo spostamento
                                spostaSx = true;
                            } else if (controllaMuroSx()) {
                                spostaDx = true;
                            } else {
                                if (!spostaSx && !spostaDx)
                                    setX(getX() + 1);
                            }

                            // Finché il nemico è sotto il player, sposta a sinistra
                            if (spostaSx)
                                setX(getX() - 1);
                            if (spostaDx)
                                setX(getX() + 1);
                        }
                    } else {
                        if (controllaMuro()) {
                            // Metto spostaSx a true per iniziare lo spostamento
                            spostaSx = true;
                        } else if (controllaMuroSx()) {
                            spostaDx = true;
                        } else {
                            if (!spostaSx && !spostaDx)
                                setX(getX() + 1);
                        }

                        // Finché il nemico è sotto il player, sposta a sinistra
                        if (spostaSx)
                            setX(getX() - 1);
                        if (spostaDx)
                            setX(getX() + 1);
                    }
                }
            }
        }

        // TODO: Se c'è un ostacolo lo salto aggiornando anche le y

        // Se il nemico è sopra di me, dovrò saltare

    }

    public boolean collidesWith(PlatformModel platform) {
        // Controlla se il personaggio è sopra la piattaforma e se la parte inferiore
        // del personaggio è al di sopra del punto superiore della piattaforma

        if (enemySpeed >= 0
                && getY() + DISTANCEPG >= platform.getY()
                && getY() <= platform.getY() + platform.getHeight()
                && getX() + DISTANCEPG >= platform.getX()
                && getX() <= platform.getX() + platform.getWidth()
                && getY() + DISTANCEPG <= platform.getY() + platform.getHeight()) {
            // La parte inferiore del personaggio è sopra il punto superiore della
            // piattaforma
            // La parte superiore del personaggio è al di sotto della piattaforma

            // Regola la posizione del personaggio per farlo rimanere sulla piattaforma
            setY(platform.getY() - DISTANCEPG);

            // --- Se sono a terra, controllo se il giocatore sta più sopra di me ---
            // Il -20 serve a rimediare alla distanza di Y tra player e enemy

            if (checkY(pm.getY(), getY())) {
                // --- Controllo se sono alla fine della piattaforma ---
                // Sottraggo 35 di offset per far pensare all'enemy di essere a fine piattaforma
                // prima di arrivare al margine vero
                if (pm.isJumping() == false) {
                    inAlto = true;
                }

                // Se sono alla fine della piattaforma
                if (getX() == platform.getWidth() - 15) {
                }

            }
            // --- Altrimenti, se il giocatore è sulla mia stessa piattaforma ---
            else {
                if (pm.isJumping() == false)
                    // System.out.println("falso, non sei in alto" + (yEnemy-35) + " " +
                    // yGiocatore);
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

        for (WallModel wm : walls) {
            // Se la X del nemico - 10 è minore o uguale alla X del muro
            if (getX() - 40 <= wm.getX() && wm.getX() <= 0) {
                muroCheck = true;
            }
        }

        return muroCheck;
    }

    // Controlla muro destro
    public boolean controllaMuro() {
        boolean muroCheck = false;

        for (WallModel wm : walls) {
            // Se la X del nemico + 10 è maggiore o uguale alla X del muro (che è 845) e la
            // X del muro non è 0 (per escludere il muro iniziale)...
            if (getX() + 40 >= wm.getX() && wm.getX() != 0) {
                muroCheck = true;
            }
        }

        return muroCheck;
    }

    public boolean isPlatformAbove() {
        for (PlatformModel platModel : platformArray) {
            // Controlla se c'è una piattaforma sopra il nemico
            if (getX() >= platModel.getX() && getX() <= platModel.getX() + platModel.getWidth()
                    && getY() - 100 < platModel.getY() && platModel.getX() != 25) {
                return true;
            }
        }
        return false;
    }

    public boolean checkY(int yGiocatore, int yEnemy) {
        if (yEnemy - 35 > yGiocatore)
            return true;
        else
            return false;
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }

    public boolean getColliding() {
        return colliding;
    }

    public double getEnemySpeed() {
        return enemySpeed;
    }

    public void setEnemySpeed(int enemySpeed) {
        this.enemySpeed = enemySpeed;
    }

    public void kill() {
        if (!isFruit()) {
            setInBubble(true);
            setFruit(false);
        }
    }

    public void instaKill() {
        if (!isFruit()) {
            setFruit(true);
            setInBubble(false);
        }
    }
}