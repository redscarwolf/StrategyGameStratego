package de.htwg.stratego.controller.impl;

import de.htwg.stratego.model.IPlayer;

public class PlayerTransfer implements GameState {
	
	private AbstractStrategoController sc;
	private IPlayer player;
	
	public PlayerTransfer(IPlayer player, AbstractStrategoController sc) {
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
	public boolean isSwapAllowed() {
		return false;
	}
	
	@Override
	public boolean isRemoveAllowed() {
		return false;
	}

	@Override
	public boolean isFinishAllowed() {
		return true;
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

	@Override
	public String getName() {
		return "transfer";
	}
}
