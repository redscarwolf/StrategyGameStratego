package de.htwg.stratego.model;

public abstract class AbstractCharacter implements Character {

	protected int rang;
	protected boolean moveable;
	protected int player;
	
	public AbstractCharacter(int rang, boolean moveable, int player) {
		this.rang = rang;
		this.moveable = moveable;
		this.player = player;
	}
	
	public int getPlayer() {
		return player;
	}
	
	public int getRang() {
		return rang;
	}
	
	public boolean isMoveable() {
		return moveable;
	}
	
	@Override
	public String toString() {
		return "" + rang;
	}
	
}
