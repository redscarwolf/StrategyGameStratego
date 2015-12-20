package de.htwg.stratego.model;

public interface IField {

	int getWidth();
	int getHeight();
	
	ICell getCell(int x, int y);
	
}
