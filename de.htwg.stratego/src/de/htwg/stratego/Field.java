package de.htwg.stratego;

public class Field {

	private int width;
	private int height;

	private Cell[][] cells;

	public Field(int width, int height) {
		this.width = width;
		this.height = height;
		
		cells = new Cell[width] [height];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				cells[x][y] = new Cell(x, y);
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Cell getCell(int x, int y) {
		return cells[x][y];
	}
	
}
