package de.htwg.stratego.model.impl;

import java.util.ArrayList;
import java.util.List;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.impl.character.Bomb;
import de.htwg.stratego.model.impl.character.Captain;
import de.htwg.stratego.model.impl.character.Colonel;
import de.htwg.stratego.model.impl.character.Flag;
import de.htwg.stratego.model.impl.character.General;
import de.htwg.stratego.model.impl.character.Lieutenant;
import de.htwg.stratego.model.impl.character.Major;
import de.htwg.stratego.model.impl.character.Marshal;
import de.htwg.stratego.model.impl.character.Miner;
import de.htwg.stratego.model.impl.character.Scout;
import de.htwg.stratego.model.impl.character.Sergeant;
import de.htwg.stratego.model.impl.character.Spy;

public class Player implements IPlayer {

	private List<ICharacter> characterList;
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
		initCharacterList();
		this.symbol = symbol;
	}
	
	@Override
	public List<ICharacter> getCharacterList() {
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
	public boolean containsCharacter(int rank) {
		return getCharacter(rank) != null;
	}
	
}
