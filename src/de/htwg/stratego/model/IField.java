package de.htwg.stratego.model;

public interface IField {

	int getWidth();
	int getHeight();
	
	ICell getCell(int x, int y);
	int getNumberOfCharacters(int rank, IPlayer player);
	
}
