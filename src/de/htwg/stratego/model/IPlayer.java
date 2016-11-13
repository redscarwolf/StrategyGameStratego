package de.htwg.stratego.model;

import java.awt.Color;
import java.util.List;

public interface IPlayer {

	List<ICharacter> getCharacterList();
	String getCharacterListAsString();
	
	void addCharacter(ICharacter c);
	boolean removeCharacter(ICharacter c);
	ICharacter removeCharacter(int index);
	ICharacter getCharacter(int rank);
	boolean containsCharacter(int rank);
	Color getColor();
	
	void setName(String name);
	String getName();
}
