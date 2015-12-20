package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.ICell;

public class Cell implements ICell {
	private int x;
	private int y;
	private boolean passable;
	private ICharacter character;
	
	public Cell(int x, int y) {
		this(x, y, true);
	}

	public Cell(int x, int y, boolean passable) {
		this.x = x;
		this.y = y;
		this.passable = passable;
	}
	
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
	
	@Override
	public void setPassable(boolean passable) {
		this.passable = passable;
	}

	@Override
	public boolean isPassable() {
		return passable;
	}
	
	@Override
	public void setCharacter(ICharacter c) {
		character = c;
	}
	
	@Override
	public ICharacter getCharacter() {
		return character;
	}
	
	@Override
	public boolean containsCharacter() {
		return character != null;
	}

	@Override
	public ICharacter removeCharacter() {
		ICharacter c = character;
		character = null;
		return c;
	}
	
	@Override
	public boolean equals(Object o) {
		Cell cell = (Cell) o;
		return (x == cell.getX() && y == cell.getY());
	}
	
	@Override
	public String toString() {
		if (passable) {
			if (character == null) {
				return "   ";
			}
			return character.toString();
		}
		return "|||";
	}

}
