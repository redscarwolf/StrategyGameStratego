package de.htwg.stratego.controller.impl;

import de.htwg.stratego.model.IPlayer;

public class PlayerOneStart implements GameState {

	private StrategoController sc;
	
	public PlayerOneStart(StrategoController sc) {
		this.sc = sc;
	}

	@Override
	public IPlayer getCurrentPlayer() {
		return sc.getPlayerOne();
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
		return "Set your characters, player 1!";
	}

	@Override
	public void changeState() {
		sc.setState(new PlayerTwoStart(sc));
		sc.toggleVisibilityOfCharacters(sc.getCurrentPlayer(), true);
	}

}
