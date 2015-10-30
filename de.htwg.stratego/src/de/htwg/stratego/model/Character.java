package de.htwg.stratego.model;

public interface Character {
	
	public static final int FLAG_RANG = 0;
	public static final int SERGEANT_RANG = 4;
	
	public int getRang();
	public boolean isMoveable();
	public int getPlayer();
}
