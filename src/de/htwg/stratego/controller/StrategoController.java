package de.htwg.stratego.controller;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IFieldFactory;
import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.impl.Player;
import de.htwg.stratego.model.impl.Rank;
import de.htwg.stratego.util.observer.Observable;

public class StrategoController extends Observable {
	private GameStatus status = GameStatus.WELCOME;
	private IField field;
	
	public static final int WIDTH_FIELD = 10;
	public static final int HEIGHT_FIELD = 10;
	
	private IPlayer playerOne;
	private IPlayer playerTwo;
	
	private GameState gameState;
	
	public StrategoController(IFieldFactory fieldFactory) {
		field = fieldFactory.create(WIDTH_FIELD, HEIGHT_FIELD);
		//TODO: impl.Player ersetzen / PlayerFactory
		playerOne = new Player("#");
		playerTwo = new Player("!");
		
		gameState = new PlayerOneStart(this);
		
		// some cells are not passable
		field.getCell(2, 4).setPassable(false);
		field.getCell(3, 4).setPassable(false);
		field.getCell(2, 5).setPassable(false);
		field.getCell(3, 5).setPassable(false);
		
		field.getCell(6, 4).setPassable(false);
		field.getCell(7, 4).setPassable(false);
		field.getCell(6, 5).setPassable(false);
		field.getCell(7, 5).setPassable(false);
		
	}
		
	public void setGameStatus(GameStatus status) {
		this.status = status;
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
	}
	
	public void changeStateNotify() {
		changeState();
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
	
	public GameState getGameState() {
		return gameState;
	}
	
	public IPlayer getCurrentPlayer() {
		return gameState.getCurrentPlayer();
	}
	
	public void move(int fromX, int fromY, int toX, int toY) {
		if (!isMoveAllowed()) {
			status = GameStatus.ILLEGAL_ARGUMENT;
		} else {
			Move move = new Move(fromX, fromY, toX, toY, this);
			boolean moveSuccess = move.execute();
			if (moveSuccess) {
				changeState();
				if (lost(getCurrentPlayer())) {
					gameOver();
				}
			} else {
				status = GameStatus.ILLEGAL_ARGUMENT;
			}
		}
		notifyObservers();
	}
	
	public boolean isMoveAllowed() {
		return gameState.isMoveAllowed();
	}
	
	public boolean isAddAllowed() {
		return gameState.isAddAllowed();
	}
	
	public boolean isRemoveAllowed() {
		return gameState.isRemoveAllowed();
	}

	private void gameOver() {
		setGameStatus(GameStatus.GAME_OVER);
		IPlayer currentPlayer = getCurrentPlayer();
		if (currentPlayer == playerOne) {
			setState(new PlayerTwoWinner(this));
		} else if (currentPlayer == playerTwo) {
			setState(new PlayerOneWinner(this));
		}
		setVisibilityOfAllCharacters(true);
	}
	
	public void add(int x, int y, int rank) {
		if (!isAddAllowed()) {
			return;
		}
		IPlayer p = gameState.getCurrentPlayer();

		// looking for char-rank in charList
		ICharacter character = p.getCharacter(rank);
		if (character == null) {
			// didn't found char-rank 
			return;
		}
		
		ICell cell = field.getCell(x, y);
		if (cell.containsCharacter()) {
			// field already has a char
			return;
		}

		if (!cell.isPassable()) {
			return;
		}
		
		// take char from list and add to field
		p.removeCharacter(character);
		cell.setCharacter(character);
		notifyObservers();
	}

	public void removeNotify(int x, int y) {
		if (!isRemoveAllowed()) {
			return;
		}
		remove(x, y);
		notifyObservers();
	}
	
	public void remove(int x, int y) {
		remove(x, y, gameState.getCurrentPlayer());
	}
	
	public void remove(int x, int y, IPlayer player) {
		ICell cell = field.getCell(x, y);
		ICharacter character = field.getCell(x, y).getCharacter();
		if (cell.containsCharacter() && character.belongsTo(player)) {
			cell.removeCharacter();
			player.addCharacter(character);
		} else {
			status = GameStatus.ILLEGAL_ARGUMENT;
		}
	}
	
	public void toggleVisibilityOfCharacters(IPlayer player, boolean visible) {
		for (int y = 0; y < field.getHeight(); y++) {
			for (int x = 0; x < field.getWidth(); x++) {
				ICell cell = field.getCell(x, y);
				if (!cell.containsCharacter()) {
					continue;
				}
				ICharacter character = cell.getCharacter();
				if (character.belongsTo(player)) {
					character.setVisible(visible);
				} else {
					character.setVisible(!visible);
				}
			}
		}
	}
	
	public void setVisibilityOfAllCharacters(boolean visible) {
		for (int y = 0; y < field.getHeight(); y++) {
			for (int x = 0; x < field.getWidth(); x++) {
				ICell cell = field.getCell(x, y);
				if (!cell.containsCharacter()) {
					continue;
				}
				cell.getCharacter().setVisible(visible);
			}
		}
	}

	public boolean lost(IPlayer player) {
		return player.containsCharacter(Rank.FLAG);
	}

	public String getFieldString() {
		return field.toString();
	}

}
