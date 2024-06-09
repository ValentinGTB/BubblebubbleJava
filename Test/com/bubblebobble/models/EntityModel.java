package com.bubblebobble.models;

public class EntityModel {
	private int x;
	private int y;

	private int width;
	private int height;

	public EntityModel() {
	}

	public EntityModel(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void moveX(int delta) {
		setX(getX() + delta);
	}

	// Restituisce la posizione x del personaggio
	public int getX() {
		// System.out.println("X aggiornate --> " + x);
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setDimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

	public boolean collidesWith(EntityModel entity) {
		if (entity == null)
			return false;

		// prima vediamo se l'entità in input è contenuta nell'entità attuale, e poi viceversa
		boolean intersectOnX = 
			getX() <= entity.getX() && entity.getX() <= getX() + getWidth()
			|| getX() <= entity.getX() + entity.getWidth() && entity.getX() + entity.getWidth() <= getX() + getWidth()
			|| entity.getX() <= getX() && getX() <= entity.getX() + entity.getWidth()
			|| entity.getX() <= getX() + getWidth() && getX() + getWidth() <= entity.getX() + entity.getWidth();

		boolean intersectOnY = 
			getY() <= entity.getY() && entity.getY() <= getY() + getHeight()
			|| getY() <= entity.getY() + entity.getHeight() && entity.getY() + entity.getHeight() <= getY() + getHeight()
			|| entity.getY() <= getY() && getY() <= entity.getY() + entity.getHeight()
			|| entity.getY() <= getY() + getHeight() && getY() + getHeight() <= entity.getY() + entity.getHeight();

		return intersectOnX && intersectOnY;
	}
}
