package de.htwg.stratego.controller;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IPlayer;

public interface GameState {

	IPlayer getCurrentPlayer();
	String getFieldString();
	ICharacter remove(int x, int y);
	void add(int x, int y, int rank);
	void moveChar(int fromX, int fromY, int toX, int toY);
	String toStringPlayerStatus();
	void changeState();
}
