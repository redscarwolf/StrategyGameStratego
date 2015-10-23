package de.htwg.stratego.model;

public class Cell {
	private int x;
	private int y;
	private Character character;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setCharacter(Character c) {
		character = c;
	}
	
	public Character getCharacter() {
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
