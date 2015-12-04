package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.ICell;

public class Cell implements ICell {
	private int x;
	private int y;
	private ICharacter character;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
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
	public void setCharacter(ICharacter c) {
		character = c;
	}
	
	@Override
	public ICharacter getCharacter() {
		return character;
	}
	
	@Override
	public boolean equals(Object o) {
		Cell cell = (Cell) o;
		if (x == cell.getX() && y == cell.getY()) {
			return true;
		}
		return false;
	}
	
}
