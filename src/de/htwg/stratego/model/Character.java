package de.htwg.stratego.model;

public interface Character {
	
	public static final int FLAG_RANG = 0;
	public static final int SERGEANT_RANG = 4;
	
	public static final int PLAYER_ONE = 1;
	public static final int PLAYER_TWO = 2;
	
	public int getRang();
	public boolean isMoveable();
	public int getPlayer();
}
