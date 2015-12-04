package de.htwg.stratego.controller;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IFieldFactory;
import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.impl.Player;
import de.htwg.stratego.util.observer.Observable;

public class StrategoController extends Observable {
	private GameStatus status = GameStatus.WELCOME;
	private IField field;
	
	private static final int WIDTH_FIELD = 10;
	private static final int HEIGHT_FIELD = 10;
	
	private IPlayer playerOne;
	private IPlayer playerTwo;
	
	private GameState gameState;
	
	private IFieldFactory fieldFactory;
	
	public StrategoController(IFieldFactory fieldFactory) {
		this.fieldFactory = fieldFactory;
		setFieldSize(WIDTH_FIELD, HEIGHT_FIELD);
		//TODO: impl.Player ersetzen / PlayerFactory
		playerOne = new Player("#");
		playerTwo = new Player("!");
		
		gameState = new PlayerOneStart(this);
		fillField();
	}
		
	public IPlayer getPlayerOne() {
		return playerOne;
	}
	
	public IPlayer getPlayerTwo() {
		return playerTwo;
	}
	
	public String toStringCharacterList(IPlayer player) {
		return player.getCharacterListAsString();
	}
	
	public void changeState() {
		gameState.changeState();
		notifyObservers();
	}
	
	public void setState(GameState s) {
		gameState = s;
	}
	
	public GameStatus getStatus() {
		return status;
	}
	
	public String toStringPlayerStatus() {
		return gameState.toStringPlayerStatus();
	}
	
	public IField getField() {
		return field;
	}
	
	public void setFieldSize(int width, int height) {
		field = fieldFactory.create(width, height);
		//TODO: abfragen von illegalen größen -1 etc.S
	}
	
	public void fillField() {
		// fill Player1 left to right, up to middle
		for (int x = 0; x < field.getWidth(); x++) {
			for (int y = 0; y < 4; y++) {		
				field.getCell(x, y).setCharacter(playerOne.removeCharacter(0));
			}
		}
		// fill Player2 right to left, bottom to middle
		for (int x = field.getWidth() - 1; x > -1; x--) {
			for (int y = field.getHeight() - 1; y > field.getHeight() - 5; y--) {		
				field.getCell(x, y).setCharacter(playerTwo.removeCharacter(0));
			}
		}
	}
	
	public void moveChar(int fromX, int fromY, int toX, int toY) {
		gameState.moveChar(fromX, fromY, toX, toY);
		notifyObservers();
	}
	
	public boolean moveChar(int fromX, int fromY, int toX,
			int toY, IPlayer player) {
		// check is move inside of Field 
		//get Cells and get Characters
		ICell fromCell = field.getCell(fromX, fromY);
		ICell toCell = field.getCell(toX, toY);
		ICharacter fromCharacter = fromCell.getCharacter();
		ICharacter toCharacter = toCell.getCharacter();
		
		//Conditions of fromCharacter
		// does selected cell contain a character
		if (fromCharacter == null) {
			notifyObservers();
			return false;
		}
		
		// is Char moveable 
		if (!fromCharacter.isMoveable()) {
			notifyObservers();
			return false;
		}
		
		// is character a char of the player
		// TODO falls gameState != playerOne turn oder player two turn
		if (fromCharacter.getPlayer() != player) {
			notifyObservers();
			return false;
		}
		
		// correct range of move
		int dx = Math.abs(fromX - toX);
		int dy = Math.abs(fromY - toY);
		if (dx > 1 || dy > 1 || dx == dy) {
			//TODO
			notifyObservers();
			return false;
		}
		
		//Conditions of toCharacter
		if (toCharacter == null) {
			// if Cell is empty move Character to new position
			changePosition(fromCell, toCell);
		} else {
			// if Cell is not empty fight with toCharacter
			// only if toCharacter.getPlayer() is not equal to fromCharacter.getPlayer() 
			if (toCharacter.getPlayer() == fromCharacter.getPlayer()) {
				notifyObservers();
				return false;
			}
			fight(fromCell, toCell);
		}
		System.out.println("ende von move");

		return true;
	}
	
	private void fight(ICell c1, ICell c2) {
		// get Character rank
		int r1 = c1.getCharacter().getRank();
		int r2 = c2.getCharacter().getRank();
		
		if (r1 > r2) {
			//success
			remove(c2.getX(),c2.getY());
			// move Character to toCell
			changePosition(c1, c2);
		} else if (r1 < r2) {
			// lost
			remove(c1.getX(),c1.getY());
		} else {
			// equal both lose
			remove(c1.getX(),c1.getY());
			remove(c2.getX(),c2.getY());
		}
	}

	private void changePosition(ICell fromCell, ICell toCell) {
		ICharacter ch = fromCell.getCharacter();
		fromCell.setCharacter(null);
		toCell.setCharacter(ch);
	}
	
	public void add(int x, int y, int rank) {
		IPlayer p = gameState.getCurrentPlayer();

		// looking for char-rank in charList
		ICharacter character = p.getCharacter(rank);
		
		if (character == null) {
			// didn't found char-rank 
			return;
		}
		
		ICell cell = field.getCell(x, y);
		if (cell.getCharacter() != null) {
			// field already has a char
			return;
		}
		
		// take char from list and add to field
		p.removeCharacter(character);
		cell.setCharacter(character);
		
		notifyObservers();
	}
	
	public void removeNotify(int x, int y) {
		remove(x, y);
		notifyObservers();
	}
	
	private ICharacter remove(int x, int y) {
		IPlayer p = gameState.getCurrentPlayer();
		ICharacter c = field.getCell(x, y).getCharacter();
		
		field.getCell(x, y).setCharacter(null);
		if (c != null) {
			p.addCharacter(c);
		}
		
		return c;
	}
	
	public String getFieldString() {
		return gameState.getFieldString();
	}

}
