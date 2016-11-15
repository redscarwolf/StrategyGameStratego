package de.htwg.stratego.controller.impl;

public interface GameState {

	void changeState();
	
	boolean isMoveAllowed();
	boolean isAddAllowed();
	boolean isSwapAllowed();
	boolean isRemoveAllowed();
	
	String getName();
	
	String toStringPlayerStatus();

}
