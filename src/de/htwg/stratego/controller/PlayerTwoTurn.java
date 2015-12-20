package de.htwg.stratego.controller;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IPlayer;

public class PlayerTwoTurn implements GameState {

	private StrategoController sc;
	
	public PlayerTwoTurn(StrategoController sc) {
		this.sc = sc;
	}

	@Override
	public IPlayer getCurrentPlayer() {
		return sc.getPlayerTwo();
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
		return "It's your turn, player 2!";
	}

	@Override
	public void changeState() {
		sc.setState(new PlayerOneTurn(sc));
		sc.toggleVisibilityOfCharacters(sc.getCurrentPlayer(), true);
	}
	
}
