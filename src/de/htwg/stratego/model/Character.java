package de.htwg.stratego.model;

public interface Character {
	
	public static final int FLAG_RANK = 0;
	public static final int SERGEANT_RANK = 4;
	
	public static final int PLAYER_ONE = 1;
	public static final int PLAYER_TWO = 2;
	
	public int getRank();
	public boolean isMoveable();
	public int getPlayer();
}
