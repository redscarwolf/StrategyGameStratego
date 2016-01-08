package de.htwg.stratego.controller.impl;

public interface GameState {

	void changeState();
	
	boolean isMoveAllowed();
	boolean isAddAllowed();
	boolean isRemoveAllowed();
	String toStringPlayerStatus();

}
