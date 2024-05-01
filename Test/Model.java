public class Model {
    private int x = 50;
    private int y = 200;
    private int xSpeed = 0;
    private int ySpeed = 0;
    private int gravity = 1;
    private int platformX = 0;
    private int platformY = 200;
    private int platformWidth = 400;
    private int platformHeight = 20;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPlatformX() {
        return platformX;
    }

    public int getPlatformY() {
        return platformY;
    }

    public int getPlatformWidth() {
        return platformWidth;
    }

    public int getPlatformHeight() {
        return platformHeight;
    }

    public void move() {
        x += xSpeed;
        y += ySpeed;
        ySpeed += gravity;

        // Tracciamento tocco della piattaforma
        if (y + 50 >= platformY && y <= platformY + platformHeight && x + 50 >= platformX && x <= platformX + platformWidth) {
            y = platformY - 50; // Set della posizione del personaggio su piattaforma
            ySpeed = 0;
        }

        if (x < 0) {
            x = 0;
        }

        if (x > 350) {
            x = 350;
        }

        if (y < 0) {
            y = 0;
        }

        if (y > 250) {
            y = 250;
        }
    }

    public void setXSpeed(int speed) {
        xSpeed = speed;
    }

    public void setYSpeed(int speed) {
        ySpeed = speed;
    }

    public void setYSpeedManual(int speed) {
        ySpeed = speed;
    }

    public void setPlatformPosition(int x, int y, int width, int height) {
        platformX = x;
        platformY = y;
        platformWidth = width;
        platformHeight = height;
    }
}
