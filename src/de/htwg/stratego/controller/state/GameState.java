package de.htwg.stratego.controller.state;

import de.htwg.stratego.model.EGameState;

public interface GameState {

	void changeState();
	
	boolean isMoveAllowed();
	boolean isAddAllowed();
	boolean isSwapAllowed();
	boolean isRemoveAllowed();
	boolean isFinishAllowed();

	String getName();
	EGameState getEGameState();
	
	String toStringPlayerStatus();

}
