package de.htwg.stratego.controller.impl;

import de.htwg.stratego.model.IPlayer;

public class PlayerTwoWinner implements GameState {

	private StrategoController sc;
	
	public PlayerTwoWinner(StrategoController sc) {
		this.sc = sc;
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
		return "Player 2 won!";
	}

	@Override
	public void changeState() {
		
	}

}
