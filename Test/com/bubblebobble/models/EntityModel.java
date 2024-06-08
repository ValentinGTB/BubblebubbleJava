package com.bubblebobble.models;

public class EntityModel {
    private int x;
	private int y;

    public EntityModel() {
    }

    public EntityModel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
		this.x = x;
	}

    public void moveX(int delta) {
		setX(getX() + delta);
	}

	// Restituisce la posizione x del personaggio
	public int getX() {
		//System.out.println("X aggiornate --> " + x);
		return x;
	}

    public void moveY(int delta) {
		setY(getY() + delta);
	}

	public void setY(int y) {
		this.y = y;
	}

	// Restituisce la posizione y del personaggio
	public int getY() {
		return y;
	}
}
