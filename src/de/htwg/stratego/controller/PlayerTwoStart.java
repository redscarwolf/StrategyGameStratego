package de.htwg.stratego.controller;

import de.htwg.stratego.model.IPlayer;

public class PlayerTwoStart implements GameState {

	private StrategoController sc;
	
	public PlayerTwoStart(StrategoController sc) {
		this.sc = sc;
	}

	@Override
	public IPlayer getCurrentPlayer() {
		return sc.getPlayerTwo();
	}

	@Override
	public boolean isMoveAllowed() {
		return false;
	}

	@Override
	public boolean isAddAllowed() {
		return true;
	}

	@Override
	public boolean isRemoveAllowed() {
		return true;
	}
	
	@Override
	public String toStringPlayerStatus() {
		return "Set your characters, player 2!";
	}

	@Override
	public void changeState() {
		sc.setState(new PlayerOneTurn(sc));
		sc.toggleVisibilityOfCharacters(sc.getCurrentPlayer(), true);
	}
	
}
