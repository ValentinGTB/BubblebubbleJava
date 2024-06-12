package com.bubblebobble.models;

import java.util.List;
import java.util.Optional;

public class BaseEnemyModel extends EnemyModel {

    /**
     * se true, muovi il nemico sopra la piattaforma più vicina.
     */
    private boolean jumpingAbovePlatform;
    private long lastJumpTime;

    public BaseEnemyModel(int x, int y) {
        super(x, y);
        this.lastJumpTime = System.currentTimeMillis();
    }

    @Override
    protected void onUpdate() {
        long currentTime = System.currentTimeMillis();

        if (!isInBubble() && !isFruit()) {
            // salta sopra la prima piattaforma sopra il nemico
            if (lastJumpTime <= 0 || currentTime - lastJumpTime >= 1000 * 2) {
                jumpingAbovePlatform = true;
            }

            if (jumpingAbovePlatform) {
                List<PlatformModel> inRangePlatforms = GameModel.getInstance().getPlatforms().stream()
                        .filter(p -> p.getX() <= getX() && getX() + getWidth() <= p.getX() + p.getWidth())
                        .toList();

                Optional<PlatformModel> closerPlatform = inRangePlatforms
                        .stream()
                        .filter(p -> getY() + getHeight() > p.getY() && getY() - p.getY() <= 199)
                        .sorted((o1, o2) -> o2.getY() - o1.getY())
                        .findFirst();

                if (closerPlatform.isPresent()) {
                    // ho trovato una piattaforma su cui il nemico può salire, azzerriamo
                    setXSpeed(0);
                    setYSpeed(-getSpeed());

                    PlatformModel platform = closerPlatform.get();
                    boolean isAbove = getYSpeed() < 0
                            && platform.collidesWith(this)
                            && getY() + getHeight() <= platform.getY() + getSpeed();

                    if (isAbove) {
                        int destY = platform.getY() - getHeight();
                        setY(destY);
                        setYSpeed(0);
                        // isAbovePlatform = true;
                        jumpingAbovePlatform = false;
                        lastJumpTime = System.currentTimeMillis();

                        // se mi trovo nella parte a sx della piattaforma, la direzione è verso destra
                        if (Math.abs(getX() - platform.getX()) < Math
                                .abs(getX() - platform.getX() - platform.getWidth())) {
                            setXSpeed(getSpeed());
                        } else {
                            setXSpeed(-getSpeed());
                        }
                    }
                }
            }
        }
    }
}
