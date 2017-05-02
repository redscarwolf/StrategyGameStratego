package de.htwg.stratego.persistence.db4o;

import org.ektorp.support.CouchDbDocument;

public class PersistenceCell extends CouchDbDocument {

    private int x;
    private int y;
    private boolean passable;
    private PersistenceCharacter character;

    public PersistenceCell() {

    }

    public PersistenceCell(int x, int y, boolean passable, PersistenceCharacter character) {
        this.x = x;
        this.y = y;
        this.passable = passable;
        this.character = character;
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

    public PersistenceCharacter getCharacter() {
        return character;
    }

    public void setCharacter(PersistenceCharacter character) {
        this.character = character;
    }
}
