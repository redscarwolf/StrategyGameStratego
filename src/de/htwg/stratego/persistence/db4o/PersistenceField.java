package de.htwg.stratego.persistence.db4o;

import org.ektorp.support.CouchDbDocument;

public class PersistenceField extends CouchDbDocument {

    private int width;
    private int height;
    private PersistenceCell[][] cells;

    public PersistenceField() {

    }

    public PersistenceField(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new PersistenceCell[width][height];
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public PersistenceCell[][] getCells() {
        return cells;
    }

    public void setCells(PersistenceCell[][] cells) {
        this.cells = cells;
    }

    public PersistenceCell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setCell(int x, int y, PersistenceCell cell) {
        cells[x][y] = cell;
    }
}
