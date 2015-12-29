package de.htwg.stratego.controller.impl;

import de.htwg.stratego.model.IPlayer;

public class PlayerOneWinner implements GameState {

	
	public PlayerOneWinner(StrategoController sc) {
	}
	
	@Override
	public IPlayer getCurrentPlayer() {
		return null;
	}

	@Override
	public boolean isMoveAllowed() {
		return false;
	}

	@Override
	public boolean isAddAllowed() {
		return false;
	}

	@Override
	public boolean isRemoveAllowed() {
		return false;
	}
	
	@Override
	public String toStringPlayerStatus() {
		return "Player 1 won!";
	}

	@Override
	public void changeState() {
		
	}

}
