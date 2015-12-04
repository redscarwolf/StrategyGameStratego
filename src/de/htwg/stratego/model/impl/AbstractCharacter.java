package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Character;

public abstract class AbstractCharacter implements Character {

	protected int rank;
	protected boolean moveable;
	protected Player player;

	public AbstractCharacter(int rank, boolean moveable, Player player) {
		this.rank = rank;
		this.moveable = moveable;
		this.player = player;
	}

	@Override
	public Player getPlayer() {
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
		return String.format("%s%2s", player, Integer.toString(rank));
	}
	
}
