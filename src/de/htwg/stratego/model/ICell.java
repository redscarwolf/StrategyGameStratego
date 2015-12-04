package de.htwg.stratego.model;

public interface ICell {

	int getX();
	int getY();
	
	void setCharacter(ICharacter c);
	ICharacter getCharacter();
	
	@Override
	boolean equals(Object o);
}
