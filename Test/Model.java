public class Model {
    private int x = 50;
    private int y = 200;
    private int xSpeed = 0;
    private int ySpeed = 0;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move() {
        x += xSpeed;
        y += ySpeed;

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
}
