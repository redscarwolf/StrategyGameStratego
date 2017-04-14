package de.htwg.stratego.model;

import java.util.List;

public interface IField {

	int getWidth();
	int getHeight();
	
	ICell getCell(int x, int y);
	List<ICell> getCells();
	List<ICell> getAllCellsFrom(IPlayer player);
	int getNumberOfCharacters(int rank, IPlayer player);
	
}
