package de.htwg.stratego.model;

public interface Character {
	
	public static final int FLAG_RANK = 0;
	public static final int SPY_RANK = 1;
	public static final int SCOUT_RANK = 2;
	public static final int MINER_RANK = 3;
	public static final int SERGEANT_RANK = 4;
	public static final int LIEUTENANT_RANK = 5;
	public static final int CAPTAIN_RANK = 6;
	public static final int MAJOR_RANK = 7;
	public static final int COLONEL_RANK = 8;
	public static final int GENERAL_RANK = 9;
	public static final int MARSHAL_RANK = 10;
	public static final int BOMB_RANK = 11;
	
	public static final int PLAYER_ONE = 1;
	public static final int PLAYER_TWO = 2;
	
	public int getRank();
	public boolean isMoveable();
	public int getPlayer();

}
