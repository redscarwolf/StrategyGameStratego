package de.htwg.stratego.model.impl;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IPlayer;

import java.util.ArrayList;
import java.util.List;

public class Player implements IPlayer {

	private List<ICharacter> characterList;
	private String symbol;
	private String name;
	private boolean setupFinished;
	
	public Player() {
		this("noname", "?");
	}

	@Inject
	public Player(String name, @Named("symbolPlayerOne") String symbol) {
		characterList = new ArrayList<>();
		this.symbol = symbol;
		this.name = name;
	}

	@Override
	public List<ICharacter> getCharacterList() {
		return characterList;
	}

	@Override
	public void addCharacter(ICharacter c) {
		if (c != null) {
			characterList.add(c);
		}
	}

	@Override
	public boolean removeCharacter(ICharacter c) {
		return characterList.remove(c);
	}

	@Override
	public ICharacter getCharacter(int rank) {
		ICharacter character = null;
		for (ICharacter c : characterList) {
			if (c.getRank() == rank) {
				character = c;
			}
		}
		return character;
	}

	@Override
	public ICharacter removeCharacter(int index) {
		return characterList.remove(index);
	}

	@Override
	public String getCharacterListAsString() {
		StringBuilder sb = new StringBuilder("|");
		for (ICharacter c : characterList) {
			sb.append(c.getRank() + "|");
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return symbol;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String getSymbol() {
		return symbol;
	}

	@Override
	public boolean hasCharacter(int rank) {
		return getCharacter(rank) != null;
	}

	public boolean hasSetupFinished() {
		return setupFinished;
	}

	public void setSetupFinished(boolean setupFinished) {
		this.setupFinished = setupFinished;
	}
}
