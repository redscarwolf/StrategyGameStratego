package de.htwg.stratego.controller.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import de.htwg.stratego.controller.IStrategoController;
import de.htwg.stratego.controller.rules.IRuleSystem;
import de.htwg.stratego.controller.rules.impl.DefaultRuleSystem;
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

	private String statusMessage = "Welcome to HTWG Stratego!";
	private GameState gameState;
	
	private UndoManager undoManager = new UndoManager();
	
	private IRuleSystem ruleSystem;
	
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
		this.ruleSystem = new DefaultRuleSystem(field);
		
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
				removeCharacterToPlayer(x, y);
			}
		}
		statusMessage = "New Game";
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
		return statusMessage;
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
		statusMessage = "GAME OVER!";
		setState(new PlayerWinner(nextChangePlayer()));
		setVisibilityOfAllCharacters(true);
	}

	@Override
	public void undo() {
		undoManager.undoCommand();
		toggleVisibilityOfCharacters(getCurrentPlayer(), true);
		statusMessage = "Undo.";
		notifyObservers();
	}
	
	public boolean lost(IPlayer player) {
		return player.hasCharacter(Rank.FLAG);
	}

	@Override
	public boolean add(int x, int y, int rank) {
		boolean result;
		IPlayer player = getCurrentPlayer();
		
		if (ruleSystem.verifyAdd(x, y, rank, player, getGameState())) {
			undoManager.doCommand(new AddCommand(field.getCell(x, y), player, player.getCharacter(rank), this));
			statusMessage = "Added character <" + nameOfCharacter(rank) + "> to " + x + "," + y + ".";
			result = true;
		} else {
			statusMessage = ruleSystem.message();
			result = false;
		}
		
		notifyObservers();
		return result;
	}
	
	@Override
	public boolean swap(int x1, int y1, int x2, int y2) {
		boolean result;
		
		if (ruleSystem.verifySwap(x1, y1, x2, y2, getCurrentPlayer(), getGameState())) {
			undoManager.doCommand(new SwapCommand(field.getCell(x1, y1), field.getCell(x2, y2), this));
			statusMessage = "Swaped characters from " + x1 + "," + y1 + " to" + x2 + "," + y2 + ".";
			result = true;
		} else {
			statusMessage = ruleSystem.message();
			result = false;
		}
		
		notifyObservers();
		return result;
	}

	@Override
	public boolean remove(int x, int y) {
		boolean result;
		
		if (ruleSystem.verifyRemove(x, y, getCurrentPlayer(), getGameState())) {
			undoManager.doCommand(new RemoveCommand(field.getCell(x, y), this));
			statusMessage = "Removed character from " + x + "," + y + ".";
			result = true;
		} else {
			statusMessage = ruleSystem.message();
			result = false;
		}
		
		notifyObservers();
		return result;
	}
	
	public void removeCharacterToPlayer(int x, int y) {
		if (containsCharacter(x, y)) {
			ICharacter character = field.getCell(x, y).removeCharacter();
			character.getPlayer().addCharacter(character);
		}
	}
	
	@Override
	public boolean move(int fromX, int fromY, int toX, int toY) {
		boolean result;
		
		if (ruleSystem.verifyMove(fromX, fromY, toX, toY, getCurrentPlayer(), getGameState())) {
			MoveCommand moveCommand = new MoveCommand(fromX, fromY, toX, toY, this);
			undoManager.doCommand(moveCommand);
			changeState();
			statusMessage = moveCommand.getMoveStatusString();
			if (lost(getCurrentPlayer())) {
				gameOver();
			}
			result = true;
		} else {
			statusMessage = ruleSystem.message();
			result = false;
		}

		notifyObservers();
		return result;
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
