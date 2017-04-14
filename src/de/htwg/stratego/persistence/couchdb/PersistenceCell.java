package de.htwg.stratego.persistence.couchdb;

import org.ektorp.support.CouchDbDocument;

public class PersistenceCell extends CouchDbDocument {

    private int x;
    private int y;
    private boolean passable;
    private PersistenceCharacter persistenceCharacter;

    public PersistenceCell(int x, int y, boolean passable, PersistenceCharacter persistenceCharacter) {
        this.x = x;
        this.y = y;
        this.passable = passable;
        this.persistenceCharacter = persistenceCharacter;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isPassable() {
        return passable;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public PersistenceCharacter getPersistenceCharacter() {
        return persistenceCharacter;
    }

    public void setPersistenceCharacter(PersistenceCharacter persistenceCharacter) {
        this.persistenceCharacter = persistenceCharacter;
    }
}
