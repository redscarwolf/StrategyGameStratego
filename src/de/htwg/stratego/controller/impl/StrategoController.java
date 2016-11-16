package de.htwg.stratego.controller.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.google.inject.Inject;

import de.htwg.stratego.controller.IStrategoController;
import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.IPlayerFactory;
import de.htwg.stratego.model.impl.Rank;
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
import de.htwg.stratego.util.command.UndoManager;
import de.htwg.stratego.util.observer.Observable;

public class StrategoController extends Observable implements IStrategoController {

	private IField field;
	
	private IPlayer[] player;
	private int currentPlayer;

	private String statusController = "Welcome to HTWG Stratego!";
	private GameState gameState;
	
	private UndoManager undoManager = new UndoManager();
	
	private static final String[] CHARACTER_NAMES = {"Flag", "Spy", "Scout", "Miner", "Sergeant", 
													"Lieutenant", "Captain", "Major", "Colonel",
													"General", "Marshal", "Bomb"};

	private static final int[] NUMBER_OF_CHARACTERS = {1, 1, 8, 5, 4, 4, 4, 3, 2, 1, 1, 6};
	
	@Inject
	public StrategoController(IField field, IPlayerFactory playerFactory) {
		player = new IPlayer[2];
		player[0] = playerFactory.create("PlayerOne", "#", Color.BLUE);
		player[1] = playerFactory.create("PlayerTwo", "!", Color.RED);
		currentPlayer = 0;
		
		initPlayerCharecterList(getPlayerOne());
		initPlayerCharecterList(getPlayerTwo());
		
		gameState = new PlayerStart(player[currentPlayer], this);

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
		currentPlayer = 0;
		gameState = new PlayerStart(getCurrentPlayer(), this);
		setVisibilityOfAllCharacters(true);
		for (int x = 0; x < field.getWidth(); x++) {
			for (int y = 0; y < field.getHeight(); y++) {
				remove(x, y, getPlayerOne());
				remove(x, y, getPlayerTwo());
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

	public IPlayer[] getPlayer() {
		return player;
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
	
	@Override
	public IPlayer getCurrentPlayer() {
		return player[currentPlayer];
	}
	
	public void setCurrentPlayer(int p) {
		currentPlayer = p;
	}
	
	@Override
	public IPlayer getPlayerOne() {
		return player[0];
	}
	
	@Override
	public IPlayer getPlayerTwo() {
		return player[1];
	}
	
	@Override
	public void setNameOfPlayerOne(String name) {
		getPlayerOne().setName(name);
	}

	@Override
	public void setNameOfPlayerTwo(String name) {
		getPlayerTwo().setName(name);
	}

	@Override
	public String getNameOfPlayerOne() {
		return getPlayerOne().getName();
	}

	@Override
	public String getNameOfPlayerTwo() {
		return getPlayerTwo().getName();
	}
	
	private void initPlayerCharecterList(IPlayer player) {
		Bomb.buildSeveral(NUMBER_OF_CHARACTERS[Rank.BOMB], player);
		Marshal.buildSeveral(NUMBER_OF_CHARACTERS[Rank.MARSHAL], player);
		General.buildSeveral(NUMBER_OF_CHARACTERS[Rank.GENERAL], player);
		Colonel.buildSeveral(NUMBER_OF_CHARACTERS[Rank.COLONEL], player);
		Major.buildSeveral(NUMBER_OF_CHARACTERS[Rank.MAJOR], player);
		Captain.buildSeveral(NUMBER_OF_CHARACTERS[Rank.CAPTAIN], player);
		Lieutenant.buildSeveral(NUMBER_OF_CHARACTERS[Rank.LIEUTENANT], player);
		Sergeant.buildSeveral(NUMBER_OF_CHARACTERS[Rank.SERGEANT], player);
		Miner.buildSeveral(NUMBER_OF_CHARACTERS[Rank.MINER], player);
		Scout.buildSeveral(NUMBER_OF_CHARACTERS[Rank.SCOUT], player);
		Spy.buildSeveral(NUMBER_OF_CHARACTERS[Rank.SPY], player);
		Flag.buildSeveral(NUMBER_OF_CHARACTERS[Rank.FLAG], player);
	}
	
	@Override
	public int numberOfCharactersOnField(int rank, IPlayer player) {
		return field.getNumberOfCharacters(rank, player);
	}
	
	public int nextPlayer() {
		return (currentPlayer + 1) % player.length;
	}

	public IPlayer nextChangePlayer() {
		currentPlayer = nextPlayer();
		return player[currentPlayer];
	}
	
	@Override
	public boolean containsCharacter(int x, int y) {
		return field.getCell(x, y).containsCharacter();
	}

	@Override
	public ICharacter getCharacter(int x, int y) {
		return field.getCell(x, y).getCharacter();
	}

	@Override
	public String nameOfCharacter(int rank) {
		if (rank < 0 || rank >= CHARACTER_NAMES.length) {
			return "noname";
		}
		return CHARACTER_NAMES[rank];
	}

	@Override
	public int maxNumberOfCharactersPerPlayer() {
		int number = 0;
		for (int rank = 0; rank < NUMBER_OF_CHARACTERS.length; rank++) {
			number += maxNumberOfCharactersPerPlayer(rank);
		}
		return number;
	}
	
	@Override
	public int maxNumberOfCharactersPerPlayer(int rank) {
		return NUMBER_OF_CHARACTERS[rank];
	}
	
	@Override
	public int numberOfDifferentCharacterTypes() {
		return CHARACTER_NAMES.length;
	}

	@Override
	public boolean isPassable(int x, int y) {
		return field.getCell(x, y).isPassable();
	}
	
	public boolean isMoveAllowed() {
		return gameState.isMoveAllowed();
	}

	public boolean isAddAllowed() {
		return gameState.isAddAllowed();
	}
	
	public boolean isSwapAllowed() {
		return gameState.isSwapAllowed();
	}

	public boolean isRemoveAllowed() {
		return gameState.isRemoveAllowed();
	}
	
	public IField getIField() {
		return field;
	}

	private void gameOver() {
		statusController = "GAME OVER!";
		setState(new PlayerWinner(nextChangePlayer()));
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
	public boolean move(int fromX, int fromY, int toX, int toY) {
		if (!isMoveAllowed()) {
			statusController = "Moving of Characters is not allowed.";
			notifyObservers();
			return false;
		} else {
			Move move = new Move(fromX, fromY, toX, toY, this);
			boolean moveSuccess = move.execute();
			if (moveSuccess) {
				undoManager.doCommand(move);
				changeState();
				statusController = move.getMoveStatusString();
				if (lost(getCurrentPlayer())) {
					gameOver();
				}
				notifyObservers();
				return true; 
			} else {
				statusController = "Your move was not possible. Try again.";
				notifyObservers();
				return false;
			}
		}
	}

	public boolean lost(IPlayer player) {
		return player.containsCharacter(Rank.FLAG);
	}

	@Override
	public boolean add(int x, int y, int rank) {
		if (!isAddAllowed()) {
			statusController = "Add is not allowed";
			notifyObservers();
			return false;
		}
		
		IPlayer p = getCurrentPlayer();
		ICharacter character = p.getCharacter(rank);
		if (character == null) {
			statusController = "all Characters of type <" + rank + "> are on the field.";
			notifyObservers();
			return false;
		}

		ICell cell = field.getCell(x, y);
		if (cell.containsCharacter()) {
			// field already has a char
			statusController = "field already has a char";
			notifyObservers();
			return false;
		}

		if (!cell.isPassable()) {
			statusController = "cell (" + x + "," + y + ") is not passable";
			notifyObservers();
			return false;
		}

		undoManager.doCommand(new AddCommand(cell, p, character, this));
		statusController = "added Character <<" + character.getRank() + ">> to (" + x + "," + y + ")";
		notifyObservers();
		return true;
	}
	
	@Override
	public boolean swap(int x1, int y1, int x2, int y2) {
		if (!isSwapAllowed()) {
			statusController = "Swap is not allowed";
			notifyObservers();
			return false;
		}
		
		ICell cell1 = field.getCell(x1, y1);
		ICell cell2 = field.getCell(x2, y2);
		
		if (!(cell1.containsCharacter())) {
			statusController = "There is no character to swap.";
			notifyObservers();
			return false;
		}
		
		if (cell1 == cell2) {
			statusController = "Swap not possible. Selected cells are equal.";
			notifyObservers();
			return false;
		}
		
		if (!(cell1.getCharacter().belongsTo(getCurrentPlayer()))) {
			statusController = "Selected character does not belong to you.";
			notifyObservers();
			return false;
		}
		
		if (cell2.containsCharacter()) {
			if (!(cell2.getCharacter().belongsTo(getCurrentPlayer()))) {
				statusController = "Second character does not belong to you.";
				notifyObservers();
				return false;
			}
		}
		
		undoManager.doCommand(new SwapCommand(cell1, cell2, this));
		statusController = "swaped Characters <<" + cell1.getX() + "," + cell1.getY() + ">> and <<" + 
								cell2.getX() + "," + cell2.getY() + ">>";
		notifyObservers();
		return true;
	}

	@Override
	public boolean removeNotify(int x, int y) {
		if (!isRemoveAllowed()) {
			statusController = "remove is not allowed.";
			notifyObservers();
			return false;
		}
		remove(x, y);
		notifyObservers();
		return true;
	}

	public void remove(int x, int y) {
		remove(x, y, getCurrentPlayer());
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

	public String toJson() {
		String result= "";

		HashMap<String, Object> selectJson = new HashMap<>();
		List<HashMap<String, Object>> characterList = new ArrayList<HashMap<String, Object>>();
		for (ICharacter character : getCurrentPlayer().getCharacterList()) {
			HashMap<String, Object> characterJson = new HashMap<>();
			characterJson.put("rank", character.getRank());
			characterJson.put("player", character.getPlayer().getName());
			characterList.add(characterJson);
		}
		selectJson.put("characterList", characterList);
		
		Map<String, Object> fieldJson = new HashMap<String,Object>();

		List<HashMap<String, Object>> innerField = new ArrayList<HashMap<String, Object>>();
		for (int column = 0; column < field.getWidth(); column++) {
			for (int row = 0; row < field.getHeight(); row++) {
				innerField.add(getCellMap(column, row));
			}
		}
		fieldJson.put("innerField", innerField);

		HashMap<String, Object> infoJson = new HashMap<>();
		List<HashMap<String, Object>> infoList = new ArrayList<HashMap<String, Object>>();
		for (int rank = 0; rank < CHARACTER_NAMES.length; rank++) {
			HashMap<String, Object> infoItemJson = new HashMap<>();
			infoItemJson.put("characterName", nameOfCharacter(rank));
			infoItemJson.put("currentCharactersPlayerOne", numberOfCharactersOnField(rank, getPlayerOne()));
			infoItemJson.put("currentCharactersPlayerTwo", numberOfCharactersOnField(rank, getPlayerTwo()));
			infoItemJson.put("maxCharacters", maxNumberOfCharactersPerPlayer(rank));
			infoList.add(infoItemJson);
		}
		infoJson.put("infoList", infoList);
		
		Map<String, Object> strategoJson = new HashMap<String,Object>();
		strategoJson.put("playerOne", getNameOfPlayerOne());
		strategoJson.put("playerTwo", getNameOfPlayerTwo());
		strategoJson.put("state", gameState.getName());
		strategoJson.put("controllerStatus", getStatusString());
		strategoJson.put("playerStatus", getPlayerStatusString());
		strategoJson.put("select", selectJson);
		strategoJson.put("field", fieldJson);
		strategoJson.put("info", infoJson);

		ObjectMapper mapper = new ObjectMapper();

		try {
			result = mapper.writeValueAsString(strategoJson);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return result;
	}

	private HashMap<String, Object> getCellMap(int column, int row) {
		HashMap<String, Object> cellMap = new HashMap<String, Object>();

		ICell cell = field.getCell(column, row);
		cellMap.put("row", cell.getY());
		cellMap.put("column", cell.getX());
		cellMap.put("isPassable", cell.isPassable());
		boolean containsChar = cell.containsCharacter();
		cellMap.put("containsCharacter", containsChar);
		
		if (containsChar) {			
			Map<String, Object> characterMap = new HashMap<String,Object>();
			ICharacter character = cell.getCharacter();
			characterMap.put("rank", character.getRank());
			characterMap.put("player", character.getPlayer().getName());
			characterMap.put("isVisible", character.isVisible());
			
			cellMap.put("character", characterMap);
		}
		return cellMap;
	}

}
