package de.htwg.stratego.controller.impl;

import de.htwg.stratego.model.IPlayer;

public class PlayerTurn implements GameState {

	private StrategoController sc;
	private IPlayer player;
	
	public PlayerTurn(IPlayer player, StrategoController sc) {
		this.sc = sc;
		this.player = player;
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
		return "It's your turn, player " + player + "!";
	}

	@Override
	public void changeState() {
		sc.setState(new PlayerTransfer(sc.nextPlayer(), sc));
		sc.setVisibilityOfAllCharacters(false);
	}
	
}
