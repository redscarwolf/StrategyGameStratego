package de.htwg.stratego.model.impl;

import java.util.ArrayList;
import java.util.List;

import de.htwg.stratego.model.Character;
import de.htwg.stratego.model.Rank;

public class Player {

	private List<Character> characterList;
	private String symbol;
	
	private static final int NUMBER_OF_BOMB = 6;
	private static final int NUMBER_OF_MARSHAL = 1;
	private static final int NUMBER_OF_GENERAL = 1;
	private static final int NUMBER_OF_COLONEL = 2;
	private static final int NUMBER_OF_MAJOR = 3;
	private static final int NUMBER_OF_CAPTAIN = 4;
	private static final int NUMBER_OF_LIEUTENANT = 4;
	private static final int NUMBER_OF_SERGEANT = 4;
	private static final int NUMBER_OF_MINER = 5;
	private static final int NUMBER_OF_SCOUT = 8;
	private static final int NUMBER_OF_SPY = 1;
	private static final int NUMBER_OF_FLAG = 1;
	
	public Player() {
		this("?");
	}
	
	public Player(String symbol) {
		characterList = new ArrayList<>();
		this.symbol = symbol;
	}
	
	public void addCharacter(Character c) {
		characterList.add(c);
	}
	
	public void removeCharacter(Character c) {
		characterList.remove(c);
	}
	
	public void addCharacters(int rank, int n) {
		switch (rank) {
		case Rank.BOMB:
			
		}
	}

	public Character getCharacter(int rank) {
		Character character = null;
		for (Character c: characterList) {
			if (c.getRank() == rank) {
				character = c;
			}
		}
		return character;
	}
	
	public Character removeCharacter(int index) {
		return characterList.remove(index);
	}
	
	public String getCharacterListAsString() {
		StringBuilder sb = new StringBuilder("|");
		for (Character c : characterList) {
			sb.append(c.getRank() + "|");
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return symbol;
	}
	
}
