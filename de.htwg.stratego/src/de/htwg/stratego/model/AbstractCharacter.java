package de.htwg.stratego.model;

public abstract class AbstractCharacter implements Character {

	protected int rang;
	protected boolean moveable;
	
	public AbstractCharacter(int rang, boolean moveable) {
		this.rang = rang;
		this.moveable = moveable;
	}
	
	public int getRang() {
		return rang;
	}
	
	public boolean isMoveable() {
		return moveable;
	}
	
}
