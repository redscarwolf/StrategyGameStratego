package de.htwg.stratego.controller.impl;

import de.htwg.stratego.model.IPlayer;

public interface GameState {

	void changeState();
	
	IPlayer getCurrentPlayer();
	boolean isMoveAllowed();
	boolean isAddAllowed();
	boolean isRemoveAllowed();
	String toStringPlayerStatus();

}
