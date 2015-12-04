package de.htwg.stratego.model;

import de.htwg.stratego.model.impl.Cell;

public interface IField {

	int getWidth();
	int getHeight();
	
	Cell getCell(int x, int y);
	
	@Override
	boolean equals(Object o);
	
	@Override
	String toString();
	
	String getFieldString(IPlayer player);
	
}
