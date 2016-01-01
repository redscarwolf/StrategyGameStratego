package de.htwg.stratego.controller.impl;

import com.google.inject.Inject;

import de.htwg.stratego.controller.IStrategoController;
import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.IPlayerFactory;
import de.htwg.stratego.model.impl.Rank;
import de.htwg.stratego.util.command.UndoManager;
import de.htwg.stratego.util.observer.Observable;

public class StrategoController extends Observable implements IStrategoController {

	private IField field;
	private IPlayer playerOne;
	private IPlayer playerTwo;

	private String statusController = "Welcome to HTWG Stratego!";
	private GameState gameState;
	
	private UndoManager undoManager = new UndoManager();
	
	@Inject
	public StrategoController(IField field, IPlayerFactory playerFactory) {
		playerOne = playerFactory.create("#");
		playerTwo = playerFactory.create("!");

		gameState = new PlayerOneStart(this);

		this.field = field;
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
	
	@Override
	public void reset() {
		gameState = new PlayerOneStart(this);
		for (int x = 0; x < field.getWidth(); x++) {
			for (int y = 0; y < field.getHeight(); y++) {
				remove(x, y, playerOne);
				remove(x, y, playerTwo);
			}
		}
		statusController = "New Game";
		undoManager.clear();
		notifyObservers();
	}
	
	@Override
	public int getFieldWidth() {
		return field.getWidth();
	}
	
	@Override
	public int getFieldHeight() {
		return field.getHeight();
	}
	
	@Override
	public String getStatusString() {
		return statusController;
	}

	@Override
	public IPlayer getPlayerOne() {
		return playerOne;
	}

	@Override
	public IPlayer getPlayerTwo() {
		return playerTwo;
	}

	@Override
	public String getCharacterListString(IPlayer player) {
		return player.getCharacterListAsString();
	}

	@Override
	public void changeStateNotify() {
		changeState();
		notifyObservers();
	}

	public void changeState() {
		gameState.changeState();
	}

	public void setState(GameState s) {
		gameState = s;
	}

	public GameState getGameState() {
		return gameState;
	}

	@Override
	public String getPlayerStatusString() {
		return gameState.toStringPlayerStatus();
	}

	public IPlayer getCurrentPlayer() {
		return gameState.getCurrentPlayer();
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
	
	@Override
	public IField getIField() {
		return field;
	}

//	public void fillField() {
//		// fill Player1 left to right, up to middle
//		for (int x = 0; x < field.getWidth(); x++) {
//			for (int y = 0; y < 4; y++) {
//				field.getCell(x, y).setCharacter(playerOne.removeCharacter(0));
//			}
//		}
//		// fill Player2 right to left, bottom to middle
//		for (int x = field.getWidth() - 1; x > -1; x--) {
//			for (int y = field.getHeight() - 1; y > field.getHeight() - 5; y--) {
//				field.getCell(x, y).setCharacter(playerTwo.removeCharacter(0));
//			}
//		}
//	}

	private void gameOver() {
		statusController = "GAME OVER!";
		IPlayer currentPlayer = getCurrentPlayer();
		if (currentPlayer == playerOne) {
			setState(new PlayerTwoWinner(this));
		} else if (currentPlayer == playerTwo) {
			setState(new PlayerOneWinner(this));
		}
		setVisibilityOfAllCharacters(true);
	}

	@Override
	public void undo() {
		undoManager.undoCommand();
		toggleVisibilityOfCharacters(getCurrentPlayer(), true);
		statusController = "Undo.";
		notifyObservers();
	}
	
	@Override
	public void move(int fromX, int fromY, int toX, int toY) {
		if (!isMoveAllowed()) {
			statusController = "Moving of Characters is not allowed.";
		} else {
			Move move = new Move(fromX, fromY, toX, toY, this);
			boolean moveSuccess = move.execute();
			if (moveSuccess) {
				undoManager.doCommand(move);
				changeState();
				statusController = "You moved from (" + fromX + "," + fromY + ") to ("
								 + toX + "," + toY + ")";
				if (lost(getCurrentPlayer())) {
					gameOver();
				}
			} else {
				statusController = "Your move was not possible. Try again.";
			}
		}
		notifyObservers();
	}

	public boolean lost(IPlayer player) {
		return player.containsCharacter(Rank.FLAG);
	}

	@Override
	public void add(int x, int y, int rank) {
		if (!isAddAllowed()) {
			statusController = "Add is not allowed";
			notifyObservers();
			return;
		}
		
		IPlayer p = gameState.getCurrentPlayer();
		ICharacter character = p.getCharacter(rank);
		if (character == null) {
			statusController = "all Characters of type <" + rank + "> are on the field.";
			notifyObservers();
			return;
		}

		ICell cell = field.getCell(x, y);
		if (cell.containsCharacter()) {
			// field already has a char
			statusController = "field already has a char";
			notifyObservers();
			return;
		}

		if (!cell.isPassable()) {
			statusController = "cell (" + x + "," + y + ") is not passable";
			notifyObservers();
			return;
		}

		undoManager.doCommand(new AddCommand(cell, p, character, this));
		statusController = "added Character <<" + character.getRank() + ">> to (" + x + "," + y + ")";
		notifyObservers();
	}

	@Override
	public void removeNotify(int x, int y) {
		if (!isRemoveAllowed()) {
			statusController = "remove is not allowed.";
			notifyObservers();
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
			undoManager.doCommand(new RemoveCommand(cell, player, character, this));
		} else {
			statusController = "There is no character or "
					+ "you are not allowed to remove this character.";
			return;
		}
		statusController = "removed Character from (" + x + "," + y + ")";
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

	@Override
	public String getFieldString() {
		return field.toString();
	}

}
