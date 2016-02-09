package de.htwg.stratego.controller.impl;

import de.htwg.stratego.model.IPlayer;

public class PlayerStart implements GameState {

	private StrategoController sc;
	private IPlayer player;
	
	public PlayerStart(IPlayer player, StrategoController sc) {
		this.sc = sc;
		this.player = player;
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
		return "Set your characters, player " + player + "!";
	}

	@Override
	public void changeState() {
		IPlayer[] player = sc.getPlayer();
		if (sc.getCurrentPlayer() == player[player.length - 1]) {
			sc.setState(new PlayerTransfer(sc.nextPlayer(), sc));
			sc.setVisibilityOfAllCharacters(false);
		} else {
			sc.setState(new PlayerStart(sc.nextPlayer(), sc));
			sc.toggleVisibilityOfCharacters(sc.getCurrentPlayer(), true);
		}
	}

}
