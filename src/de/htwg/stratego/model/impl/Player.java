package de.htwg.stratego.model.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IPlayer;

public class Player implements IPlayer {

	private List<ICharacter> characterList;
	private String symbol;
	private Color color;
	private String name;
	
	public Player() {
		this("noname", "?", Color.PINK);
	}

	@Inject
	public Player(String name, @Named("symbolPlayerOne") String symbol, Color color) {
		characterList = new ArrayList<>();
		this.color = color;
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
	public Color getColor() {
		return color;
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
	public boolean hasCharacter(int rank) {
		return getCharacter(rank) != null;
	}

}
