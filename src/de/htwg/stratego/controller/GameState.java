package de.htwg.stratego.controller;

import de.htwg.stratego.model.Character;
import de.htwg.stratego.model.impl.Player;

public interface GameState {

	Player getCurrentPlayer();
	String getFieldString();
	Character remove(int x, int y);
	void add(int x, int y, int rank);
	void moveChar(int fromX, int fromY, int toX, int toY);
	String toStringPlayerStatus();
	void changeState();
	
}
