package de.htwg.stratego.model;

import de.htwg.stratego.model.impl.Player;

public interface Character {
	
	public static final int PLAYER_ONE = 1;
	public static final int PLAYER_TWO = 2;
	
	public int getRank();
	public boolean isMoveable();
	public Player getPlayer();

}
