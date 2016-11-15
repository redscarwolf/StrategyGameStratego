package de.htwg.stratego.controller.impl;

import de.htwg.stratego.model.IPlayer;

public class PlayerWinner implements GameState {

	private IPlayer player;
	
	public PlayerWinner(IPlayer player) {
		this.player = player;
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
	public boolean isSwapAllowed() {
		return false;
	}
	
	@Override
	public boolean isRemoveAllowed() {
		return false;
	}
	
	@Override
	public String toStringPlayerStatus() {
		return "Player " + player + " won!";
	}

	@Override
	public void changeState() {
		return;
	}

	@Override
	public String getName() {
		return "winner";
	}

}
