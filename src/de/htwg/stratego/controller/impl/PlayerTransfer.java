package de.htwg.stratego.controller.impl;

import de.htwg.stratego.model.IPlayer;

public class PlayerTransfer implements GameState {
	
	private StrategoController sc;
	private IPlayer player;
	
	public PlayerTransfer(IPlayer player, StrategoController sc) {
		this.sc = sc;
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
	public boolean isRemoveAllowed() {
		return false;
	}

	@Override
	public String toStringPlayerStatus() {
		return player + " please press Button to continue.";
	}

	@Override
	public void changeState() {
		sc.setState(new PlayerTurn(sc.getCurrentPlayer(), sc));
		sc.toggleVisibilityOfCharacters(sc.getCurrentPlayer(), true);
	}
}
