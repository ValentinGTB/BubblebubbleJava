import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class View extends JPanel {

    private Model model;

    public View(Model model) {
        this.model = model;

        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.move();
                repaint();
            }
        });
        timer.start();

        setFocusable(true);
        requestFocus();
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    model.setXSpeed(-2);
                } else if (key == KeyEvent.VK_RIGHT) {
                    model.setXSpeed(2);
                } else if (key == KeyEvent.VK_UP) {
                    model.setYSpeedManual(-10); // Imposta la velocità verticale negativa per far saltare il personaggio
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                    model.setXSpeed(0);
                } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                    model.setYSpeed(0);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillRect(model.getX(), model.getY(), 50, 50);

        g.setColor(Color.GREEN); // Colore della piattaforma
        g.fillRect(model.getPlatformX(), model.getPlatformY(), model.getPlatformWidth(), model.getPlatformHeight()); // Disegna la piattaforma
    }
}
