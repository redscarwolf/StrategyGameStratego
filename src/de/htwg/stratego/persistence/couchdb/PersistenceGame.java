package de.htwg.stratego.persistence.couchdb;

import de.htwg.stratego.model.EGameState;
import org.ektorp.support.CouchDbDocument;

import java.util.List;

public class PersistenceGame extends CouchDbDocument {

    private int currentPlayer;
    private List<PersistencePlayer> player;
    private EGameState gameState;
    private PersistenceField field;

    public PersistenceGame() {

    }

    public PersistenceGame(int currentPlayer, List<PersistencePlayer> player, EGameState gameState, PersistenceField field) {
        this.currentPlayer = currentPlayer;
        this.player = player;
        this.gameState = gameState;
        this.field = field;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<PersistencePlayer> getPlayer() {
        return player;
    }

    public void setPlayer(List<PersistencePlayer> player) {
        this.player = player;
    }

    public EGameState getGameState() {
        return gameState;
    }

    public void setGameState(EGameState gameState) {
        this.gameState = gameState;
    }

    public PersistenceField getField() {
        return field;
    }

    public void setField(PersistenceField field) {
        this.field = field;
    }

}
