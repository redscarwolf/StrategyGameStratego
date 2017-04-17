package de.htwg.stratego.model;

import de.htwg.stratego.model.impl.Cell;

import java.util.List;

public interface IField {

	void setCell(ICell cell);

	int getWidth();
	int getHeight();
	
	ICell getCell(int x, int y);
	List<ICell> getCells();
	List<ICell> getAllCellsFrom(IPlayer player);
	int getNumberOfCharacters(int rank, IPlayer player);
}
