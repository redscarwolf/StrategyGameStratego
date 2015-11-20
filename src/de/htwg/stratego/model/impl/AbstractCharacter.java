package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Character;

public abstract class AbstractCharacter implements Character {

	protected int rang;
	protected boolean moveable;
	protected int player;
	
	public AbstractCharacter(int rang, boolean moveable, int player) {
		this.rang = rang;
		this.moveable = moveable;
		this.player = player;
	}
	
	@Override
	public int getPlayer() {
		return player;
	}
	
	@Override
	public int getRank() {
		return rang;
	}
	
	@Override
	public boolean isMoveable() {
		return moveable;
	}
	
	@Override
	public String toString() {
		String playerString;
		switch (player) {
			case PLAYER_ONE:
				playerString = "#";
				break;
			case PLAYER_TWO:
				playerString = "!";
				break;
			default:
				playerString = Integer.toString(player);
				break;
		}
		
		return playerString + rang;
	}
	
}
