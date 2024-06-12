package com.bubblebobble.models;

import java.util.ArrayList;

public class GhostEnemyModel extends EnemyModel {

    public GhostEnemyModel(int x, int y) {
        super(x, y);
    }
    
    @Override
    protected void onUpdate() {
        if (isFruit()) {
            setXSpeed(0);
            setYSpeed(0);
            return;
        }
        
        if (isFruit() || isInBubble()) {
            return;
        }

        if (getXSpeed() == 0 || getYSpeed() == 0) {
            setXSpeed(getSpeed());
            setYSpeed(getSpeed());
        }

        // verifica collisione con i muri, con cambio direzione di movimento
        ArrayList<EntityModel> entities = new ArrayList<>();
        entities.addAll(GameModel.getInstance().getPlatforms());
        entities.addAll(GameModel.getInstance().getWalls());

        for (EntityModel entity : entities) {
            
            boolean isPerimetral = entity.getX() == 0 || entity.getY() == 0;

            if (!isPerimetral)
                continue;

            if (entity.collidesWith(this)) {
                if (getX() <= entity.getX()) { // ho toccato un muro venendo da sx
                    setXSpeed(getXSpeed() * -1);
                } else if (entity.getX() + entity.getWidth() <= getX()) {
                    setXSpeed(getXSpeed() * -1);
                } else if (getY() >= entity.getY() + entity.getHeight()) {
                    setYSpeed(getYSpeed() * -1);
                } else {
                    setYSpeed(getYSpeed() * -1);
                }
            }
        }
    }
}
