package de.htwg.stratego.controller;

import java.util.ArrayList;
import java.util.List;

import de.htwg.stratego.model.Cell;
import de.htwg.stratego.model.Character;
import de.htwg.stratego.model.Field;
import de.htwg.stratego.model.impl.Bomb;
import de.htwg.stratego.model.impl.Captain;
import de.htwg.stratego.model.impl.Colonel;
import de.htwg.stratego.model.impl.Flag;
import de.htwg.stratego.model.impl.General;
import de.htwg.stratego.model.impl.Lieutenant;
import de.htwg.stratego.model.impl.Major;
import de.htwg.stratego.model.impl.Marshal;
import de.htwg.stratego.model.impl.Miner;
import de.htwg.stratego.model.impl.Scout;
import de.htwg.stratego.model.impl.Sergeant;
import de.htwg.stratego.model.impl.Spy;
import de.htwg.stratego.util.observer.Observable;

public class StrategoController extends Observable {
	private GameStatus status = GameStatus.WELCOME;
	private Field field;
	
	private static final int WIDTH_FIELD = 10;
	private static final int HEIGHT_FIELD = 10;
	
	private List<Character> characterListPlayer1;
	private List<Character> characterListPlayer2;
	
	private enum PlayerStatus {PLAYER_ONE_START, PLAYER_TWO_START, PLAYER_ONE_TURN, PLAYER_TWO_TURN};
	private PlayerStatus playerStatus = PlayerStatus.PLAYER_ONE_START;
	
	private static final int NUMBER_OF_BOMB = 6;
	private static final int NUMBER_OF_MARSHALL = 1;
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
	
	public StrategoController() {
		setField(WIDTH_FIELD, HEIGHT_FIELD);
		initCharacterLists();
	}
	
	private void initCharacterLists() {
		// create Lists
		characterListPlayer1 = new ArrayList<>();
		characterListPlayer2 = new ArrayList<>();
		
		// fill Lists with Chars
		addToCharList(new Bomb(Character.PLAYER_ONE),
				new Bomb(Character.PLAYER_TWO),
				NUMBER_OF_BOMB);
		
		addToCharList(new Marshal(Character.PLAYER_ONE),
				new Marshal(Character.PLAYER_TWO),
				NUMBER_OF_MARSHALL);
		
		addToCharList(new General(Character.PLAYER_ONE),
				new General(Character.PLAYER_TWO),
				NUMBER_OF_GENERAL);

		addToCharList(new Colonel(Character.PLAYER_ONE),
				new Colonel(Character.PLAYER_TWO),
				NUMBER_OF_COLONEL);

		addToCharList(new Major(Character.PLAYER_ONE),
				new Major(Character.PLAYER_TWO),
				NUMBER_OF_MAJOR);

		addToCharList(new Captain(Character.PLAYER_ONE),
				new Captain(Character.PLAYER_TWO),
				NUMBER_OF_CAPTAIN);

		addToCharList(new Lieutenant(Character.PLAYER_ONE),
				new Lieutenant(Character.PLAYER_TWO),
				NUMBER_OF_LIEUTENANT);
		
		addToCharList(new Sergeant(Character.PLAYER_ONE),
					  new Sergeant(Character.PLAYER_TWO),
					  NUMBER_OF_SERGEANT);

		addToCharList(new Miner(Character.PLAYER_ONE),
				new Miner(Character.PLAYER_TWO),
				NUMBER_OF_MINER);

		addToCharList(new Scout(Character.PLAYER_ONE),
				new Scout(Character.PLAYER_TWO),
				NUMBER_OF_SCOUT);

		addToCharList(new Spy(Character.PLAYER_ONE),
				new Spy(Character.PLAYER_TWO),
				NUMBER_OF_SPY);
		
		addToCharList(new Flag(Character.PLAYER_ONE),
				  new Flag(Character.PLAYER_TWO),
				  NUMBER_OF_FLAG);
	}
	
	private void addToCharList(Character ch1, Character ch2, int number){
	
		addNumberOfChar(characterListPlayer1,
				ch1,
				number);
		addNumberOfChar(characterListPlayer2,
			ch2,
			number);
	}
	
	private void addNumberOfChar(List<Character> charlist,
								 Character ch,
								 int number) {
		for (int i = 0; i < number; i++) {
			charlist.add(ch);
		}
	}
	
	
	public String toStringCharacterList(int player) {
		List<Character> characterList = null;
		if (player == Character.PLAYER_ONE) {
			characterList = characterListPlayer1;
		} else if (player == Character.PLAYER_TWO) {
			characterList = characterListPlayer2;
		} else {
			return  "?";
		}
		
		StringBuilder sb = new StringBuilder("|");
		for (Character c : characterList) {
			sb.append(c.getRank() + "|");
		}
		return sb.toString();
	}
	
	public void changePlayerSetup() {
		if (playerStatus == PlayerStatus.PLAYER_ONE_START) {
			playerStatus = PlayerStatus.PLAYER_TWO_START;
		} else if (playerStatus== PlayerStatus.PLAYER_TWO_START) {
			playerStatus = PlayerStatus.PLAYER_ONE_TURN;
		}
		notifyObservers();
	}
	
	public void changePlayerTurn() {
		if (playerStatus == PlayerStatus.PLAYER_ONE_TURN) {
			playerStatus = PlayerStatus.PLAYER_TWO_TURN;
		} else if (playerStatus == PlayerStatus.PLAYER_TWO_TURN) {
			playerStatus = PlayerStatus.PLAYER_ONE_TURN;
		}
	}
	
	public GameStatus getStatus() {
		return status;
	}
	
	public String toStringPlayerStatus() {
		if (playerStatus == PlayerStatus.PLAYER_ONE_START) {
			return "Set your characters, player 1!";
		} else if (playerStatus == PlayerStatus.PLAYER_TWO_START) {
			return "Set your characters, player 2!";
		} else if (playerStatus == PlayerStatus.PLAYER_ONE_TURN) {
			return "It's your turn, player 1!";
		} else if (playerStatus == PlayerStatus.PLAYER_TWO_TURN) {
			return "It's your turn, player 2!";
		}
		return null;
	}
	
	public Field getField() {
		return field;
	}
	
	public void setField(int width, int height) {
		this.field = new Field(width,height);
		//TODO: abfragen von illegalen größen -1 etc.S
	}
	
	public void fillField() {
		// fill Player1 left to right, up to middle
		for (int x = 0; x < field.getWidth(); x++) {
			for (int y = 0; y < 4; y++) {		
				field.getCell(x, y).setCharacter(characterListPlayer1.get(0));
				characterListPlayer1.remove(0);
			}
		}
		// fill Player2 right to left, bottom to middle
		for (int x = field.getWidth() - 1; x > -1; x--) {
			for (int y = field.getHeight() - 1; y > field.getHeight() - 5; y--) {		
				field.getCell(x, y).setCharacter(characterListPlayer2.get(0));
				characterListPlayer2.remove(0);
			}
		}
	}
	
	public void moveChar(int fromX, int fromY, int toX,
			int toY) {
		//TODO: check is move inside of Field 
		//get Cells and get Characters
		Cell fromCell = field.getCell(fromX, fromY);
		Cell toCell = field.getCell(toX, toY);
		Character fromCharacter = fromCell.getCharacter();
		Character toCharacter = toCell.getCharacter();
		
		//Conditions of fromCharacter
		//TODO: is selected character != null ;
		if (fromCharacter == null) {
			notifyObservers();
			return;
		}
		
		//TODO: is Char moveable 
		if (!fromCharacter.isMoveable()) {
			notifyObservers();
			return;
		}
		
		//TODO: is character a char of the player
		if (fromCharacter.getPlayer() == Character.PLAYER_ONE) {
			if (!(playerStatus == PlayerStatus.PLAYER_ONE_TURN)) {
				notifyObservers();
				return;
			}
		} else if (fromCharacter.getPlayer() == Character.PLAYER_TWO) {
			if (!(playerStatus == PlayerStatus.PLAYER_TWO_TURN)) {
				notifyObservers();
				return;
			}
		} else {
			notifyObservers();
			return;
		}
		
		// correct range of move
		int dx = Math.abs(fromX - toX);
		int dy = Math.abs(fromY - toY);
		if (dx > 1 || dy > 1 || dx == dy) {
			//TODO
			notifyObservers();
			return;
		}
		
		//Conditions of toCharacter
		if (toCharacter == null) {
			// if Cell is empty move Character to new position
			fromCell.setCharacter(null);
			toCell.setCharacter(fromCharacter);
		} else {
			// if Cell is not empty fight with toCharacter
			// only if toCharacter.getPlayer() is not equal to fromCharacter.getPlayer() 
			if (toCharacter.getPlayer() == fromCharacter.getPlayer()) {
				notifyObservers();
				return;
			}
			fight(fromCell, toCell);
		}
		System.out.println("ende von move");
		
		changePlayerTurn();
		notifyObservers();
	}
	
	private void fight(Cell c1, Cell c2) {
		//TODO gültige Zellen überprüfen
		//TODO sind auf beiden Zellen Characters
		// get Character rank
		int r1 = c1.getCharacter().getRank();
		int r2 = c2.getCharacter().getRank();
		
		if (r1 > r2) {
			//success
			remove(c2.getX(),c2.getY());
		} else if (r1 < r2) {
			// lost
			remove(c1.getX(),c1.getY());
		} else {
			// equal both lose
			remove(c1.getX(),c1.getY());
			remove(c2.getX(),c2.getY());
			
		}
	}
	
	public void add(int x, int y, int rank) {
		List<Character> characterList = null;
		
		if (playerStatus == PlayerStatus.PLAYER_ONE_START) {
			characterList = characterListPlayer1;
		} else if (playerStatus == PlayerStatus.PLAYER_TWO_START) {
			characterList = characterListPlayer2;
		} else {
			//TODO
			return;
		}
		
		// looking for char-rank in charList
		Character character = null;
		for (Character c: characterList) {
			if (c.getRank() == rank) {
				character = c;
			}
		}
		
		if (character == null) {
			// didn't found char-rank 
			return;
		}
		
		Cell cell = field.getCell(x, y);
		if (cell.getCharacter() != null) {
			// field already has a char
			return;
		}
		
		// take char from list and add to field
		characterList.remove(character);
		cell.setCharacter(character);
		
		notifyObservers();
	}
	
	public void removeNotify(int x, int y) {
		remove(x, y);
		notifyObservers();
	}
	
	private Character remove(int x, int y) {
		List<Character> characterList = null;
		
//		if (playerStatus == PlayerStatus.PLAYER_ONE_START ||
//				playerStatus == PlayerStatus.PLAYER_ONE_TURN) {
//			characterList = characterListPlayer1;
//		} else if (playerStatus == PlayerStatus.PLAYER_TWO_START ||
//				playerStatus == PlayerStatus.PLAYER_TWO_TURN) {
//			characterList = characterListPlayer2;
//		} else {
//			//TODO
//			return null;
//		}
		
		Character c = field.getCell(x, y).getCharacter();
		
		if (c.getPlayer() == Character.PLAYER_ONE) {
			characterList = characterListPlayer1;
		} else if (c.getPlayer() == Character.PLAYER_TWO) {
			characterList = characterListPlayer2;
		}
		
		field.getCell(x, y).setCharacter(null);
		if (c != null) {
			characterList.add(c);
		}
		
		return c;
	}
	
	public String getFieldString() {
		return field.toString();
	}
	
}
