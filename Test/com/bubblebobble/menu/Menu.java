package com.bubblebobble.menu;

import com.bubblebobble.Constants;
import com.bubblebobble.Main;
import com.bubblebobble.ResourceManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Menu extends JPanel {
	JButton button = new JButton();
	JButton buttonExit = new JButton();

	public Menu() {
		button.setIcon(new ImageIcon(ResourceManager.getInstance().getImage("imageButtonStart.png")));
		buttonExit.setIcon(new ImageIcon(ResourceManager.getInstance().getImage("exitButtonDef.png")));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		button.setBackground(Color.BLACK);
		button.setPreferredSize(new Dimension(0, 56)); // Cambia height
		button.setMaximumSize(new Dimension(100, 0)); // Cambia width

		buttonExit.setBackground(Color.black);

		add(Box.createVerticalStrut(200)); // Aggiungi uno spessore invisibile tra il bordo del pannello e il pulsante
		add(Box.createHorizontalStrut(Constants.MAX_WIDTH / 2 - 290));

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SwingUtilities.invokeLater(Main::new);
				Constants.MENUAPERTO = false;
			}
		});

		buttonExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		setBackground(Color.black);

		add(Box.createVerticalGlue());
		add(button);
		add(Box.createVerticalGlue());
		add(buttonExit);
		add(Box.createVerticalGlue());

		setVisible(true);
		requestFocus(); // Richiedi che questo pannello sia pronto a ricevere input da mouse o tastiera
						// appena visualizzato sopra a tutti gli altri

		repaint();

	}

	public void paintComponent(Graphics g) {
		Image image = ResourceManager.getInstance().getImage("start2.png");
		int larghezza = image.getWidth(null);
		int altezza = image.getHeight(null);

		int W = Constants.MAX_WIDTH / 2 - larghezza / 2; // Sposto a sinistra l'immagine per centrarla, spostandola di
															// quanto vale la sua metà
		int H = Constants.MAX_HEIGHT / 3 - altezza / 2;
		super.paintComponent(g);
		g.drawImage(image, W, H, this);
	}

}
