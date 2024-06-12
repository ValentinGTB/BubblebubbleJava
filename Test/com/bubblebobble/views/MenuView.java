package com.bubblebobble.views;

import com.bubblebobble.Constants;
import com.bubblebobble.BubbleBobble;
import com.bubblebobble.ResourceManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class MenuView extends JPanel {
	JButton startButton = new JButton();
	JButton exitButton = new JButton();

	public MenuView(BubbleBobble game) {
		startButton.setIcon(new ImageIcon(ResourceManager.getInstance().getImage("imageButtonStart.png")));
		exitButton.setIcon(new ImageIcon(ResourceManager.getInstance().getImage("exitButtonDef.png")));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		startButton.setBackground(Color.BLACK);
		startButton.setPreferredSize(new Dimension(0, 100)); // Cambia height
		startButton.setMaximumSize(new Dimension(200, 0)); // Cambia width

		exitButton.setBackground(Color.black);

		exitButton.setPreferredSize(new Dimension(0, 100)); // Cambia height
		exitButton.setMaximumSize(new Dimension(200, 0)); // Cambia width

		add(Box.createVerticalStrut(500)); // Aggiungi uno spessore invisibile tra il bordo del pannello e il pulsante
		add(Box.createHorizontalStrut(Constants.MAX_WIDTH / 2 - 200));

		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				game.showProfiles();
			}
		});

		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		setBackground(Color.black);

		add(Box.createVerticalGlue());
		add(startButton);
		add(Box.createVerticalGlue());
		add(exitButton);
		add(Box.createVerticalGlue());

		setVisible(true);
		requestFocus(); // Richiedi che questo pannello sia pronto a ricevere input da mouse o tastiera
						// appena visualizzato sopra a tutti gli altri

		repaint();

	}

	public void paintComponent(Graphics g) {
		Image image = ResourceManager.getInstance().getImage("start.png");
		int larghezza = image.getWidth(null);
		int altezza = image.getHeight(null);

		int W = Constants.MAX_WIDTH / 2 - larghezza / 2; // Sposto a sinistra l'immagine per centrarla, spostandola di
															// quanto vale la sua metà
		int H = Constants.MAX_HEIGHT / 3 - altezza / 2;
		super.paintComponent(g);
		g.drawImage(image, W, H, this);
	}

}
