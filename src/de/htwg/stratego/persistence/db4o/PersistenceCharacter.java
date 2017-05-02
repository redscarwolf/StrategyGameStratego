package de.htwg.stratego.persistence.db4o;

import org.ektorp.support.CouchDbDocument;

public class PersistenceCharacter extends CouchDbDocument {

    protected int rank;
    protected boolean moveable;
    protected boolean visible;
    protected PersistencePlayer player;

    public PersistenceCharacter() {

    }

    public PersistenceCharacter(int rank, boolean moveable, boolean visible, PersistencePlayer player) {
        this.rank = rank;
        this.moveable = moveable;
        this.visible = visible;
        this.player = player;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isMoveable() {
        return moveable;
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public PersistencePlayer getPlayer() {
        return player;
    }

    public void setPlayer(PersistencePlayer player) {
        this.player = player;
    }
}
