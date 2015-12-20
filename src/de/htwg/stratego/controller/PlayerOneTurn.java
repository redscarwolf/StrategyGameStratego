package de.htwg.stratego.controller;

import de.htwg.stratego.model.IPlayer;

public class PlayerOneTurn implements GameState {

	private StrategoController sc;
	
	public PlayerOneTurn(StrategoController sc) {
		this.sc = sc;
	}

	@Override
	public IPlayer getCurrentPlayer() {
		return sc.getPlayerOne();
	}

	@Override
	public boolean isMoveAllowed() {
		return true;
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
		return "It's your turn, player 1!";
	}

	@Override
	public void changeState() {
		sc.setState(new PlayerTwoTurn(sc));
		sc.toggleVisibilityOfCharacters(sc.getCurrentPlayer(), true);
	}
	
}
