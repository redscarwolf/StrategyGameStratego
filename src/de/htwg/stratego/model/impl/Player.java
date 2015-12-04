package de.htwg.stratego.model.impl;

import java.util.ArrayList;
import java.util.List;

import de.htwg.stratego.model.Character;

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
	
	public List<Character> getCharacterList() {
		return characterList;
	}
	
	private void initCharacterList(){
		Bomb.buildSeveral(NUMBER_OF_BOMB,this);
		Marshal.buildSeveral(NUMBER_OF_MARSHAL, this);
		General.buildSeveral(NUMBER_OF_GENERAL, this);
		Colonel.buildSeveral(NUMBER_OF_COLONEL, this);
		Major.buildSeveral(NUMBER_OF_MAJOR, this);
		Captain.buildSeveral(NUMBER_OF_CAPTAIN, this);
		Lieutenant.buildSeveral(NUMBER_OF_LIEUTENANT, this);
		Sergeant.buildSeveral(NUMBER_OF_SERGEANT, this);
		Miner.buildSeveral(NUMBER_OF_MINER, this);
		Scout.buildSeveral(NUMBER_OF_SCOUT, this);
		Spy.buildSeveral(NUMBER_OF_SPY, this);
		Flag.buildSeveral(NUMBER_OF_FLAG, this);
	}
	
	public Player() {
		this("?");
	}
	
	public Player(String symbol) {
		characterList = new ArrayList<>();
		initCharacterList();
		this.symbol = symbol;
	}
	
	public void addCharacter(Character c) {
		characterList.add(c);
	}
	
	public void removeCharacter(Character c) {
		characterList.remove(c);
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
