package de.htwg.stratego.model;

public class Field {

	private int width;
	private int height;

	private Cell[][] cells;

	public Field(int width, int height) {
		this.width = width;
		this.height = height;

		// field initializing
		cells = new Cell[width][height];

		// each cell initialized
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
//		if (x < 0 || x > width - 1 || y < 0 || y > height - 1) {
//			throw new IndexOutOfBoundsException();
//		}
		return cells[x][y];
	}
	
	@Override
	public boolean equals(Object o) {
		Field field = (Field) o;
		if (width == field.getWidth() && height == field.getHeight()) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder lineSb = new StringBuilder("+");
		StringBuilder mainSb = new StringBuilder();
		
		for (int i = 0; i < width; i++) {
			lineSb.append("--+");
		}
		lineSb.append("\n");
		String lineString = lineSb.toString();
		
		for (int j = 0; j < height; j++) {
			mainSb.append(lineString);
			for (int i = 0; i < width; i++) {
				if (cells[i][j].getCharacter() == null) {
					mainSb.append("|  ");
				} else {
					mainSb.append("|" + cells[i][j].getCharacter());
				}
			}
			mainSb.append("|\n");
		}
		mainSb.append(lineString);
		
		return mainSb.toString();
	}

}
