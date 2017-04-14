package de.htwg.stratego.persistence.couchdb;

import de.htwg.stratego.model.impl.Rank;
import org.ektorp.support.CouchDbDocument;

import java.util.List;

public class PersistencePlayer extends CouchDbDocument {

    private List<Integer> characterList;
    private String symbol;
    private String name;
    private boolean setupFinished;

    public PersistencePlayer(List<Integer> characterList, String symbol, String name, boolean setupFinished) {
        this.characterList = characterList;
        this.symbol = symbol;
        this.name = name;
        this.setupFinished = setupFinished;
    }

    public List<Integer> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<Integer> characterList) {
        this.characterList = characterList;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSetupFinished() {
        return setupFinished;
    }

    public void setSetupFinished(boolean setupFinished) {
        this.setupFinished = setupFinished;
    }
}
