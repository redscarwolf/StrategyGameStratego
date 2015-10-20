package de.htwg.stratego;

public class Cell {
	private int x;
	private int y;

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
	
	@Override
	public boolean equals(Object o) {
		Cell cell = (Cell) o;
		if (x == cell.getX() && y == cell.getY()) {
			return true;
		}
		return false;
	}

}
