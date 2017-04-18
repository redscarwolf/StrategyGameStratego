package de.htwg.stratego.controller.impl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htwg.stratego.controller.IStrategoController;
import de.htwg.stratego.controller.rules.IRuleSystem;
import de.htwg.stratego.controller.rules.impl.DefaultRuleSystem;
import de.htwg.stratego.controller.state.GameState;
import de.htwg.stratego.controller.state.impl.PlayerStart;
import de.htwg.stratego.controller.state.impl.PlayerTransfer;
import de.htwg.stratego.controller.state.impl.PlayerTurn;
import de.htwg.stratego.controller.state.impl.PlayerWinner;
import de.htwg.stratego.model.*;
import de.htwg.stratego.model.impl.Game;
import de.htwg.stratego.model.impl.Rank;
import de.htwg.stratego.model.impl.character.*;
import de.htwg.stratego.persistence.IDao;
import de.htwg.stratego.util.command.UndoManager;
import de.htwg.stratego.util.observer.Observable;

import java.awt.*;
import java.util.*;

public abstract class AbstractStrategoController extends Observable implements IStrategoController {

    private IDao dao;
    private IField field;

    private IPlayer[] player;
    private int currentPlayer;

    protected String statusMessage = "Welcome to HTWG Stratego!";
    private GameState gameState;

    private UndoManager undoManager = new UndoManager();

    private IRuleSystem ruleSystem;

    private static final String[] CHARACTER_NAMES = {"Flag", "Spy", "Scout", "Miner", "Sergeant",
            "Lieutenant", "Captain", "Major", "Colonel",
            "General", "Marshal", "Bomb"};

    private static final int[] NUMBER_OF_CHARACTERS = {1, 1, 8, 5, 4, 4, 4, 3, 2, 1, 1, 6};

    public AbstractStrategoController(IField field, IPlayerFactory playerFactory, IDao dao) {
        this.dao = dao;
        player = new IPlayer[2];
        player[0] = playerFactory.create("PlayerOne", "#");
        player[1] = playerFactory.create("PlayerTwo", "!");
        currentPlayer = 0;

        initPlayerCharecterList(getPlayerOne());
        initPlayerCharecterList(getPlayerTwo());

        gameState = new PlayerStart(getCurrentPlayer(), this);
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

    public void addWithoutRule(int x, int y, int rank) {
    	new AddCommand(field.getCell(x, y), getCurrentPlayer(), getCurrentPlayer().getCharacter(rank), this).doCommand();
    }
    
    public boolean add(int x, int y, int rank, IPlayer player) {
        boolean result;

        if (ruleSystem.verifyAdd(x, y, rank, player, this)) {
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

    public boolean swap(int x1, int y1, int x2, int y2, IPlayer player) {
        boolean result;

        if (ruleSystem.verifySwap(x1, y1, x2, y2, player, this)) {
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

    public boolean remove(int x, int y, IPlayer player) {
        boolean result;

        if (ruleSystem.verifyRemove(x, y, player, getGameState())) {
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

    public boolean isInCorrectZone(int x, int y, IPlayer player) {
        if (player == getPlayerOne()) {
            if (!(y < 4)) {
                return false;
            }
        } else {
            if (!(y > 5)) {
                return false;
            }
        }

        return true;
    }

    public void removeCharacterToPlayer(int x, int y) {
        if (containsCharacter(x, y)) {
            ICharacter character = field.getCell(x, y).removeCharacter();
            character.getPlayer().addCharacter(character);
        }
    }

    public boolean move(int fromX, int fromY, int toX, int toY, IPlayer player) {
        boolean result;

        if (ruleSystem.verifyMove(fromX, fromY, toX, toY, player, getGameState())) {
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

    public String toJson(IPlayer player) {
        String result= "";

        HashMap<String, Object> selectJson = new HashMap<>();
        java.util.List<HashMap<String, Object>> characterList = new ArrayList<HashMap<String, Object>>();
        for (ICharacter character : player.getCharacterList()) {
            HashMap<String, Object> characterJson = new HashMap<>();
            characterJson.put("rank", character.getRank());
            characterJson.put("player", character.getPlayer().getName());
            characterList.add(characterJson);
        }
        selectJson.put("characterList", characterList);

        Map<String, Object> fieldJson = new HashMap<String,Object>();

        java.util.List<HashMap<String, Object>> innerField = new ArrayList<HashMap<String, Object>>();
        for (int row = 0; row < field.getWidth(); row++) {
            HashMap<String, Object> fieldRowJson = new HashMap<>();
            java.util.List<HashMap<String, Object>> cellsJson = new ArrayList<HashMap<String, Object>>();
            for (int column = 0; column < field.getHeight(); column++) {
                cellsJson.add(getCellMap(column, row));
            }
            fieldRowJson.put("cells", cellsJson);
            innerField.add(fieldRowJson);
        }
        fieldJson.put("innerField", innerField);

        HashMap<String, Object> infoJson = new HashMap<>();
        java.util.List<HashMap<String, Object>> infoList = new ArrayList<HashMap<String, Object>>();
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
            boolean isVisible = character.isVisible();
            characterMap.put("isVisible", isVisible);
            if (isVisible) {
                characterMap.put("rank", character.getRank());
            }
            characterMap.put("player", character.getPlayer().getName());

            cellMap.put("character", characterMap);
        }
        return cellMap;
    }

    @Override
    public void save() {
        int defaultId = 1;
        IGame game = new Game(defaultId, currentPlayer, getPlayer(), gameState.getEGameState(), getIField());
        dao.updateGame(game);
        statusMessage = "Saved.";
        notifyObservers();
    }

    @Override
    public void load() {
        int notImplemented = 1;
        IGame game = dao.readGame(notImplemented);
        if (game == null) {
            statusMessage = "Load failed.";
            notifyObservers();
            return;
        }

        currentPlayer = game.getCurrentPlayer();
        player = game.getPlayer();

        switch (game.getGameState()) {
            case PLAYER_START:
                gameState = new PlayerStart(getCurrentPlayer(), this);
                break;
            case PLAYER_TRANSFER:
                gameState = new PlayerTransfer(getCurrentPlayer(), this);
                break;
            case PLAYER_TURN:
                gameState = new PlayerTurn(getCurrentPlayer(), this);
                break;
            case PLAYER_WIN:
                gameState = new PlayerWinner(getCurrentPlayer());
                break;
        }

        field = game.getField();
        ruleSystem = new DefaultRuleSystem(field);

        statusMessage = "Game loaded.";
        undoManager.clear();
        notifyObservers();
    }

}
