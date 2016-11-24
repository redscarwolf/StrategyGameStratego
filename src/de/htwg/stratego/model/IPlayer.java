package de.htwg.stratego.model;

import java.util.List;

public interface IPlayer {

	List<ICharacter> getCharacterList();
	String getCharacterListAsString();
	
	void addCharacter(ICharacter c);
	boolean removeCharacter(ICharacter c);
	ICharacter removeCharacter(int index);
	ICharacter getCharacter(int rank);
	boolean hasCharacter(int rank);
	
	void setName(String name);
	String getName();
	boolean hasSetupFinished();
	void setSetupFinished(boolean setupFinished);
}
