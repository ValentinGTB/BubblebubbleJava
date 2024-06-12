package com.bubblebobble.models;

import com.bubblebobble.Constants;
import com.bubblebobble.contansts.PowerUpType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.swing.Timer;

public abstract class EnemyModel extends CharacterModel {
    private boolean inBubble = false;
    private boolean isEaten = false;
    private boolean isFruit = false;
    private int speed;

    /// Allo scattare del tempo, il nemico che si trova in una bolla diventa una frutta
    private Timer bubbleTimer;

    public EnemyModel(int x, int y) {
        super(x, y, Constants.ENEMY_WIDTH, Constants.ENEMY_HEIGHT);
        setSpeed(Constants.ENEMY_SPEED);
    }

    public void update() {
        if (GameModel.getInstance().hasPowerup(PowerUpType.Freeze)
            || GameModel.getInstance().hasPowerup(PowerUpType.FreezeAndKill))
            return;

        // sposta la il nemico trasformato in bolla verso l'alto
        updateBubble();

        // controlla collisioni con i muri
        checkWallCollision();

        // aggiorna la direzione del nemico
        updateDirection();

        // sovracrive comportamento movimento
        onUpdate();
        
        // aggiorna la posizione del nemico
        move();
    }

    protected abstract void onUpdate();

    protected void updateBubble() {
        if (isInBubble()) {
            setXSpeed(0);
            boolean isBelowPlatform = false;
            for (PlatformModel platform : GameModel.getInstance().getPlatforms()) {
                isBelowPlatform = platform.collidesWith(this) && platform.getY() <= getY();

                if (isBelowPlatform) {
                    setY(platform.getY() + platform.getHeight());
                    break;
                }
            }

            if (!isBelowPlatform)
                setYSpeed(-Constants.ENEMY_BUBBLE_SPEED);
            else
                setYSpeed(0);
        }
    }

    protected void checkWallCollision() {
        // verifica collisione con i muri, con cambio direzione di movimento
        for (WallModel wall : GameModel.getInstance().getWalls()) {
            if (wall.collidesWith(this)) {
                boolean isLeftWall = wall.getX() <= getX();

                if (isLeftWall) {
                    setX(wall.getX() + wall.getWidth());
                } else {
                    setX(wall.getX() - getWidth());
                }

                setXSpeed(getXSpeed() * -1);
            }
        }
    }

    @Override
    public void move() {
        // muovi il personaggio solo se non è freezato.
        boolean canMove = !GameModel.getInstance().hasPowerup(PowerUpType.Freeze)
                && !GameModel.getInstance().hasPowerup(PowerUpType.FreezeAndKill);

        if (isFruit()) {
            setXSpeed(0);
        }

        if (canMove) {
            super.move();
        }
    }

    private void updateDirection() {
        long currentTime = System.currentTimeMillis();

        if (!isInBubble() && !isFruit()) {
            // default a sx
            if (getXSpeed() == 0) {
                setXSpeed(-speed);
            }

            // cambia direzione ogni secondo
            if (currentTime % 1001 == 0) {
                setXSpeed(getXSpeed() * -1);
            }
        }
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}