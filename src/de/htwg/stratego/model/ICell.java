package de.htwg.stratego.model;

public interface ICell {

	int getX();
	int getY();
	
	void setPassable(boolean b);
	boolean isPassable();
	
	void setCharacter(ICharacter c);
	ICharacter getCharacter();
	boolean containsCharacter();
	ICharacter removeCharacter();
	
}
