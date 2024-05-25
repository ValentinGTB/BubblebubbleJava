package com.bubblebobble.models;

import com.bubblebobble.Constants;

public class PlayerModel {

	private int x;
	private int y;
	int vita = Constants.VITA;

	private int xSpeed = 0;
	private int ySpeed = 0;

	private int DISTANCEPG = 40;
	private double gravity = 1;

	private boolean isJumping;

	// private PlayerView pw = new PlayerView();

	public PlayerModel(){}

	public PlayerModel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	// Restituisce la posizione x del personaggio
	public int getX() {
		//System.out.println("X aggiornate --> " + x);
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	// Restituisce la posizione y del personaggio
	public int getY() {
		return y;
	}

	public void setVita()
	{
		vita -= 1;
		System.out.println("DOPO SETVITA" + vita);
	}

	public int getVita()
	{
		return vita;
	}

	public void salta() {
		if (!isJumping)
			setJumping(true);
	}

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean jumping) {
		if (jumping) {
			ySpeed = -15;
			isJumping = true;
		} else {
			ySpeed = 0;
			isJumping = false;
		}
	}

	public boolean collidesWith(PlatformModel platform) {
		// Controlla se il personaggio è sopra la piattaforma e se la parte inferiore del personaggio è al di sopra del punto superiore della piattaforma
	
		if (ySpeed >= 0 
			&& y + DISTANCEPG >= platform.getPlatformY() 
			&& y <= platform.getPlatformY() + platform.getPlatformHeight()
			&& x + DISTANCEPG >= platform.getPlatformX() 
			&& x <= platform.getPlatformX() + platform.getPlatformWidth()
			&& y + DISTANCEPG <= platform.getPlatformY() + platform.getPlatformHeight()) {
			// La parte inferiore del personaggio è sopra il punto superiore della piattaforma
			// La parte superiore del personaggio è al di sotto della piattaforma
			// Il personaggio sta scendendo
	
			// Regola la posizione del personaggio per farlo rimanere sulla piattaforma
			y = platform.getPlatformY() - DISTANCEPG;
			ySpeed = 0; // Imposta la velocità verticale a 0 per fermare il movimento verso il basso
			setJumping(false); // Imposta lo stato del salto a falso
			return true; // Collisione rilevata
		}
	
		return false; // Nessuna collisione rilevata
	}


	// Muove il personaggio
	public void move() {
		x += xSpeed;
		y += ySpeed;
		ySpeed += gravity;
	}

	// Imposta la velocità x del personaggio
	public void setXSpeed(int speed) {
		xSpeed = speed;
	}

	public int getXSpeed() {
		return xSpeed;
	}

	// Imposta la velocità y del personaggio
	public void setYSpeed(int speed) {
		ySpeed = speed;
	}

	public int getYSpeed() {
		return ySpeed;
	}
}