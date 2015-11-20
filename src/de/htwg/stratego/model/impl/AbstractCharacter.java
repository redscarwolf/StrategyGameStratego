package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Character;

public abstract class AbstractCharacter implements Character {

	protected int rank;
	protected boolean moveable;
	protected int player;
	
	public AbstractCharacter(int rank, boolean moveable, int player) {
		this.rank = rank;
		this.moveable = moveable;
		this.player = player;
	}
	
	@Override
	public int getPlayer() {
		return player;
	}
	
	@Override
	public int getRank() {
		return rank;
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
		
		return String.format("%s%2s", playerString, Integer.toString(rank));
	}
	
}
